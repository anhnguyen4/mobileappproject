package com.example.sipln.mobile_dict_app.Services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.sipln.mobile_dict_app.Models.WordHelper;
import com.example.sipln.mobile_dict_app.R;
import com.example.sipln.mobile_dict_app.Views.Activities.HomeActivity;

public class NotifyService extends IntentService {

    NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "0");
    NotificationManager notificationManager;
    Intent save;
    private int pos = 0;

    public NotifyService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            save = new Intent(this, DBService.class);
            save.setAction("Save"+pos);
            save.putExtra("pos", String.valueOf(pos));
            save.putExtra("word",intent.getExtras().getString("word"));
            save.putExtra("meaning",intent.getExtras().getString("meaning"));
            pos += 1;

//            PendingIntent pendingIntentSave = PendingIntent.getActivity(this, 0, save,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);
//            remoteViews.setTextViewText(R.id.tv_notify_word_text, intent.getExtras().getString("word"));
//            remoteViews.setTextViewText(R.id.tv_notify_word_meaning, intent.getExtras().getString("meaning"));
//            remoteViews.setOnClickPendingIntent(R.id.btn_save, pendingIntentSave);

            notification
                    .setSmallIcon(R.drawable.dict)
                    .setContentTitle("Word Meaning")
                    .setContentText(intent.getExtras().getString("word") +"\t" +intent.getExtras().getString("meaning"))
                    .setStyle(new NotificationCompat.InboxStyle()
                            .addLine(intent.getExtras().getString("word"))
                            .addLine(intent.getExtras().getString("meaning"))
                    )
                    .addAction(R.drawable.ic_save, "Save", PendingIntent.getService(this, 0, save, PendingIntent.FLAG_UPDATE_CURRENT))
                    ;

            notificationManager.notify(0, notification.build());
        }
    }


}
