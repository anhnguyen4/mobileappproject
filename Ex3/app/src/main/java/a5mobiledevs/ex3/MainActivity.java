package a5mobiledevs.ex3;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        data = new ArrayList<>();
        getBtn = (Button) findViewById(R.id.button);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWebsite();
            }
        });

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
                break;
            default:
                break;
        }

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
                break;
            default:
                break;
        }

        final String finalUrl = "http://sieuthivieclam.vn/tim-viec-lam/" + job1 + location1 + ".html";

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
                    Document doc = Jsoup.connect(finalUrl).get();
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
                        mRcvAdapter = new RecyclerViewAdapter(data);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        mRecyclerView.setLayoutManager(layoutManager);
                        mRecyclerView.setAdapter(mRcvAdapter);
                    }
                });
            }
        }).start();
    }
}