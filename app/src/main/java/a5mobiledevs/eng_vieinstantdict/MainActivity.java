package a5mobiledevs.eng_vieinstantdict;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // https://developer.android.com/guide/components/services#foreground-services-should-be-noticeable-to-the-user
        Intent intent = new Intent(this, ForegroundService.class);

        // https://developer.android.com/reference/android/content/Context#startservice
        // Every call to this method will result in a corresponding call to the target service's onStartCommand(Intent, int, int) method
        startService(intent);
    }

}
