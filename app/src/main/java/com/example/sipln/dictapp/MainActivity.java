package com.example.sipln.dictapp;

import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ClipboardManager clipboardManager;
    NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "0");
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                notification
                .setSmallIcon(R.drawable.ic_notifications_24dp)
                .setContentTitle("My Notification ")
                .setContentText(clipboardManager.getPrimaryClip().getItemAt(0).getText().toString())
                ;
                notificationManager.notify(0, notification.build());
            }
        });
    }
}
