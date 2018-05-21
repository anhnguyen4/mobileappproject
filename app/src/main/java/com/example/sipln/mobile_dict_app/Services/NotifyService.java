package com.example.sipln.mobile_dict_app.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.sipln.mobile_dict_app.Models.Word;
import com.example.sipln.mobile_dict_app.R;

public class NotifyService extends IntentService {

    NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "0");
    NotificationManager notificationManager;

    public NotifyService() {
        super("");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notification
                    .setSmallIcon(R.drawable.ic_notifications__24dp)
                    .setContentTitle("Word Meaning")
                    .setContentText(intent.getExtras().getString("meaning"));
            notificationManager.notify(0, notification.build());
        }
    }

}
