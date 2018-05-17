package a5mobiledevs.eng_vieinstantdict;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BackgroundService extends Service {
    ClipboardManager clipboardManager;
    NotificationCompat.Builder notification = new NotificationCompat.Builder(this, getString(R.string.notificationChannelID));
    NotificationManager notificationManager;

    public BackgroundService() {
        createNotificationChannel();
    }

    // https://developer.android.com/training/notify-user/build-notification#Priority
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("LOL", "You are using Android 8?");
            CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.notificationChannelID), name, importance);
            //channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LOL", "Service started");
        //notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                String clipboardContent = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
                Log.i("LOL", "You have just copied something!");
                new ConnectTheLib().execute(clipboardContent);
                String translationResult = "result";
                // https://developer.android.com/training/notify-user/build-notification
                notification
                        .setSmallIcon(R.drawable.ic_notifications_24dp)
                        .setContentTitle("Translation result")
                        .setContentText(translationResult)
                ;
                notificationManager.notify(0, notification.build());
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    private static final String BASED_URL = "https://api-platform.systran.net/translation/text/translate?input=";
    private static final String endURL = "&source=en&target=vi&withSource=false&withAnnotations=false&backTranslation=false&encoding=utf-8";
    private static final String keyURL = "&key=81f68939-5120-4774-a06c-aa92e86b9a90";

    private class ConnectTheLib extends AsyncTask<String, Void, String> {
        private String datapost = null;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String thisURL = BASED_URL
                        + strings
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
            Log.d("LOL", "onPostExecute: " + result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented!");
    }
}