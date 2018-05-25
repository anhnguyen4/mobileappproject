package a5mobiledevs.ex3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;
    private TextView mTextMessage;

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

        result = (TextView) findViewById(R.id.result);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://sieuthivieclam.vn/tim-viec-lam/tim-viec-lam/category_4-ngan-hang-tu-van-tai-chinh/district_16-huyen-can-gio.html").get();
                    String title = doc.title();
                    builder.append(title).append("\n");
                    for (Element element : doc.select("a[href^=viec-lam]")) {
                        builder.append("\n").append("Job title: ").append(element.attr("title"));
                    }
                    /*Elements links = doc.select("a[href]");

                    for (Element link : links) {
                        builder.append("\n").append("Link : ").append(link.attr("href"))
                                .append("\n").append("Text : ").append(link.text());
                    }*/
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("LOL", builder.toString());
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
}