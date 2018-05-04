package com.example.sipln.dictapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {
    ClipboardManager clipboardManager;
    NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "0");
    NotificationManager notificationManager;
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

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

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
