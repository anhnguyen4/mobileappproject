package com.example.sipln.mobile_dict_app.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.sipln.mobile_dict_app.Models.WordHelper;
import com.example.sipln.mobile_dict_app.R;
import com.example.sipln.mobile_dict_app.Views.Activities.HomeActivity;

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
            Intent save = new Intent(this, DBService.class);
            save.setAction("Save");
            save.putExtra("word",intent.getExtras().getString("word"));
            save.putExtra("meaning",intent.getExtras().getString("meaning"));
            notification
                    .setSmallIcon(R.drawable.ic_notifications__24dp)
                    .setContentTitle("Word Meaning")
                    .setStyle(new NotificationCompat.InboxStyle()
                            .addLine(intent.getExtras().getString("word"))
                            .addLine(intent.getExtras().getString("meaning"))
                    )
                    .addAction(R.drawable.ic_save, "Save", PendingIntent.getService(this, 0, save, 0))
                    ;

            notificationManager.notify(0, notification.build());

        }
    }


}
