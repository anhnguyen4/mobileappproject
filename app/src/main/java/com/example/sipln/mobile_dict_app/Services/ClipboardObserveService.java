package com.example.sipln.mobile_dict_app.Services;

import com.example.sipln.mobile_dict_app.Models.Word;
import com.example.sipln.mobile_dict_app.Models.WordHelper;
import com.example.sipln.mobile_dict_app.R;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class ClipboardObserveService extends Service {
    private ClipboardManager clipboardManager;
    private Context context = this;
    public ClipboardObserveService() {
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                String searchWord = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
                WordHelper word = new WordHelper(context, new Word(searchWord));
                word.translate();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
