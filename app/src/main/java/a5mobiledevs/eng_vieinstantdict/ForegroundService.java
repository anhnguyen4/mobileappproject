package a5mobiledevs.eng_vieinstantdict;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ForegroundService extends Service implements AsyncResponse {
    ClipboardManager clipboardManager;
    NotificationCompat.Builder resultNoti = new NotificationCompat.Builder(this);
    NotificationManager notificationManager;

    private static final String BASED_URL = "https://api-platform.systran.net/translation/text/translate?input=";
    private static final String endURL = "&source=en&target=vi&withSource=false&withAnnotations=false&backTranslation=false&encoding=utf-8";
    private static final String keyURL = "&key=81f68939-5120-4774-a06c-aa92e86b9a90";

    public ForegroundService() {
        //createNotificationChannel(); // reference: https://developer.android.com/training/notify-user/build-notification#Priority
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LOL", "Service started");

        Notification noti = new Notification.Builder(this)
                .setContentTitle("Eng-Vie Instant Dict is running")
                .setContentText("Copy or cut a word to see something magical")
                .build();
        // use this method to tell it to keep the Intent running harder
        startForeground(1, noti);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                String clipboardContent = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
                Log.i("LOL", "You have just copied something!");
                String translationResult = "";
                ConnectTheLib ctlTask = new ConnectTheLib();
                //ctlTask.delegate = this;
                try {
                    translationResult = ctlTask.execute(clipboardContent).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                // https://developer.android.com/training/notify-user/build-notification
                resultNoti
                        .setSmallIcon(R.drawable.ic_notifications_24dp)
                        .setContentTitle("Translation result")
                        .setContentText(translationResult);
                Log.i("LOL", "Notified!");
                notificationManager.notify(0, resultNoti.build());
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void processFinish(String output) {

    }

    private class ConnectTheLib extends AsyncTask<String, Void, String> {
        private String datapost = null;
        public AsyncResponse delegate = null;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String thisURL = BASED_URL
                        + strings[0]
                        + endURL
                        + keyURL;
                URL url = new URL(thisURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer json = new StringBuffer(1024);
                String tmp = "";
                while ((tmp = reader.readLine()) != null)
                    json.append(tmp).append("\n");
                reader.close();
                JSONObject data = new JSONObject(json.toString());
                try {
                    JSONArray outputs = (JSONArray) data.get("outputs");
                    datapost = outputs.getJSONObject(0).getString("output");
                } catch (Exception e) {
                    datapost = data.toString();
                }

                return data.toString();

            } catch (Exception e) {
                datapost = "Cannot translate!";
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String result) {
            //Log.d("LOL", "onPostExecute: " + result);
            //notificationManager.notify(0, resultNoti.build());
            //delegate.processFinish(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}