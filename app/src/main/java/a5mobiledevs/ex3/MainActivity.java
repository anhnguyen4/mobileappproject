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
                    /*
<div class="job-item ">
    <div class="row">
        <div class="col-md-3 col-sm-3 col-xs-3 job-logo-col">
              <div class="job-logo">
                    <div class=" img-avatar  bg "   style="background-image:url(http://sieuthivieclam.vn/templates/sieuthivieclam/images/avatar.jpg)"></div>														</div>
                  </div>
                  <div class="col-md-9 col-sm-9 col-xs-9 job-info-col">
                    <div class="job-info">
                      <h3 class="title-job"><a title="Chuyên viên tín dụng" target="_blank" href="viec-lam/chuyen-vien-tin-dung-CV2017-A1947.html">Chuyên viên tín dụng </a></h3>
                      <p class="company-name"><a href="cong-ty/ngan-hang-anz-viet-nam-dn2017-40939.html" class="text-muted" title="NGÂN HÀNG ANZ VIỆT NAM" target="_blank" >NGÂN HÀNG ANZ VIỆT NAM</a></p>

                      <p class="save-job"><a alias="chuyen-vien-tin-dung-CV2017-A1947" title="Lưu công việc" class="ufav " href="#"><i class="fa fa-heart-o"></i></a></p>
                      <div class="more-info clearfix">
                        <div class="more-info-div salary-info">Lương: Thoả thuận</div>
                        <div class="more-info-div city-info"><span class="pe-7s-map-marker"></span>Hồ Chí Minh</div>
                        <div class="more-info-div date-info"><span class="pe-7s-timer"></span>Hạn nộp: 23/09/2017</div>
                      </div>
                </div>
          </div>
    </div>
</div><!-- .job-item -->
                     */
                    String title = doc.title();
                    builder.append(title).append("\n");
                    for (Element jobTitleElement : doc.select("a[href^=viec-lam]")) {
                        // jobTitleElement ís an <a> tag
                        Element jobElement = jobTitleElement.parent() // <h3>
                                .parent(); // <div class="job-info">
                        Element companyElement = jobElement.child(1) // <p class="company-name">
                                .child(0); // <a>
                        Element infoElement = jobElement.child(3); // <div class="more-info clearfix">
                        builder.append("\nCông việc: ").append(jobTitleElement.val())
                                .append("\nLink: ").append(jobTitleElement.attr("href"))
                                .append("\nDoanh nghiệp: ").append(companyElement.val()));
                    }
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