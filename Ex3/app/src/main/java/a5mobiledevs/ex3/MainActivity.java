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

<<<<<<< HEAD
    private Context context = this;
    private Button btnSearch;
    private List<String> data;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private  int i;

=======
    private Button getBtn;
    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRcvAdapter;
    List<String> data;
    private TextView mTextMessage;
    private String job1="";
    private String location1="";
    private int i;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_discover);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_saved);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
>>>>>>> f45eeddbee7cacfecd423b52a3cb03e9b74f6926

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        recyclerView = findViewById(R.id.rv_jobs);
        data = new ArrayList<>();
        btnSearch =  findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
=======
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<>();
        getBtn = (Button) findViewById(R.id.button);
        getBtn.setOnClickListener(new View.OnClickListener() {
>>>>>>> f45eeddbee7cacfecd423b52a3cb03e9b74f6926
            @Override
            public void onClick(View v) {
                getWebsite();
            }
        });

<<<<<<< HEAD

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
=======
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void getWebsite() {
        Spinner mySpinner1=(Spinner) findViewById(R.id.spn_job);
        String text1 = mySpinner1.getSelectedItem().toString();               // get content of spinner
        Spinner mySpinner2=(Spinner) findViewById(R.id.spn_location);
        String text2 = mySpinner2.getSelectedItem().toString();

        // choose dropdown
        switch (text1){
            case "Cơ khí":
                job1 = "category_1-co-khi-ky-thuat-ung-dung-tu-dong-hoa";
                break;
            case "Xây dựng":
                job1 = "category_3-thiet-ke-xay-dung";
                break;
            case "IT":
                job1 = "category_2-cong-nghe-thong-tin";
                break;
            case "Ngân hàng":
                job1 = "category_4-ngan-hang-tu-van-tai-chinh";
                break;
            case "Kế toán":
                job1 = "category_8-ke-toan-kiem-toan-thu-ngan";
>>>>>>> f45eeddbee7cacfecd423b52a3cb03e9b74f6926
                break;
            default:
                break;
        }

<<<<<<< HEAD
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
=======
        switch (text2) {
            case "HCM":
                location1 = "/location_22-ho-chi-minh";
                break;
            case "Vũng Tàu":
                location1 = "/location_62-ba-ria-vung-tau-";
                break;
            case "Cần Thơ":
                location1 = "/location_169-can-tho-";
                break;
            case "Đồng Nai":
                location1 = "/location_248-dong-nai-";
                break;
            case "Tây Ninh":
                location1 = "/location_630-tay-ninh-";
>>>>>>> f45eeddbee7cacfecd423b52a3cb03e9b74f6926
                break;
            default:
                break;
        }

<<<<<<< HEAD
        final String QueryToJobInfos = "http://sieuthivieclam.vn/tim-viec-lam/" + jobQuery + locationQuery + ".html";
=======
        final String finalUrl = "http://sieuthivieclam.vn/tim-viec-lam/" + job1 + location1 + ".html";
>>>>>>> f45eeddbee7cacfecd423b52a3cb03e9b74f6926

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
<<<<<<< HEAD
                    Document doc = Jsoup.connect(QueryToJobInfos).get();
=======
                    Document doc = Jsoup.connect(finalUrl).get();
>>>>>>> f45eeddbee7cacfecd423b52a3cb03e9b74f6926
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
<<<<<<< HEAD
                        recyclerViewAdapter = new RecyclerViewAdapter(context, data);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(recyclerViewAdapter);
=======
                        mRcvAdapter = new RecyclerViewAdapter(data);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setAdapter(mRcvAdapter);
>>>>>>> f45eeddbee7cacfecd423b52a3cb03e9b74f6926
                    }
                });
            }
        }).start();
    }
}