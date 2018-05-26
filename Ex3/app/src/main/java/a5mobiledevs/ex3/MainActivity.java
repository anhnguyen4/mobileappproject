package a5mobiledevs.ex3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context = this;
    private Button btnSearch;
    private List<String> data;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private  int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_jobs);
        data = new ArrayList<>();
        btnSearch =  findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebsite();
            }
        });


        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.navigation_saved:
                        Intent saved = new Intent(context, FavJobActivity.class);
                        saved.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(saved);
                }

                return false;
            }
        });
    }

    private void getWebsite() {
        Spinner spinnerJob= findViewById(R.id.spinnerJob);
        String job = spinnerJob.getSelectedItem().toString();               // get content of spinner
        Spinner spinnerLocation= findViewById(R.id.spinnerLocation);
        String location = spinnerLocation.getSelectedItem().toString();

        String jobQuery ="";
        String locationQuery ="";

        // choose dropdown
        switch (job){
            case "Cơ khí":
                jobQuery = "category_1-co-khi-ky-thuat-ung-dung-tu-dong-hoa";
                break;
            case "Xây dựng":
                jobQuery = "category_3-thiet-ke-xay-dung";
                break;
            case "IT":
                jobQuery = "category_2-cong-nghe-thong-tin";
                break;
            case "Ngân hàng":
                jobQuery = "category_4-ngan-hang-tu-van-tai-chinh";
                break;
            case "Kế toán":
                jobQuery = "category_8-ke-toan-kiem-toan-thu-ngan";
                break;
            default:
                break;
        }

        switch (location) {
            case "HCM":
                locationQuery = "/location_22-ho-chi-minh";
                break;
            case "Vũng Tàu":
                locationQuery = "/location_62-ba-ria-vung-tau-";
                break;
            case "Cần Thơ":
                locationQuery = "/location_169-can-tho-";
                break;
            case "Đồng Nai":
                locationQuery = "/location_248-dong-nai-";
                break;
            case "Tây Ninh":
                locationQuery = "/location_630-tay-ninh-";
                break;
            default:
                break;
        }

        final String QueryToJobInfos = "http://sieuthivieclam.vn/tim-viec-lam/" + jobQuery + locationQuery + ".html";

        new Thread(new Runnable() {
            @Override
            public void run() {
                i = 0;
                final int size = data.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        data.remove(0);
                    }
                }
                final StringBuilder builder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect(QueryToJobInfos).get();
                    String title = doc.title();
                    builder.append(title).append("\n");
                    for (Element jobTitleElement : doc.select("a[href^=viec-lam]")) {
                        builder.setLength(0);
                        i += 1;
//                        builder.append("\n").append("Job title: ").append(element.attr("title"));
                        Element jobElement = jobTitleElement.parent() // <h3>
                                .parent(); // <div class="job-info">
                        Element companyElement = jobElement.child(1) // <p class="company-name">
                                .child(0); // <a>
                        Element infoElement = jobElement.child(3); // <div class="more-info clearfix">
                        builder.append("\nCông việc: ").append(jobTitleElement.attr("title"))
                                .append("\nLink: http://sieuthivieclam.vn/").append(jobTitleElement.attr("href"))
                                .append("\nDoanh nghiệp: ").append(companyElement.attr("title")).append("\n");
                        data.add(builder.toString());
                    }

                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                    data.add(builder.toString());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (i==0) {
                            builder.append("\n\nNo works here!").append("\n");
                            data.add(builder.toString());
                        }

                        Log.i("LOL", builder.toString());
                        recyclerViewAdapter = new RecyclerViewAdapter(context, data);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                });
            }
        }).start();
    }
}