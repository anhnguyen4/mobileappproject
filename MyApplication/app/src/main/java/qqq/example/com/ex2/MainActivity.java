package qqq.example.com.ex2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    String thisURL="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){                          // listerner for button
            @Override
            public void onClick(View v){
                EditText edt_cur1 = (EditText) findViewById(R.id.edt_cur1);         // get value of currency need to convert
                String str_cur1 = edt_cur1.getText().toString();
                String endURL = "&source=en&target=vi&withSource=false&withAnnotations=false&backTranslation=false&encoding=utf-8";
                String keyURL = "&key=81f68939-5120-4774-a06c-aa92e86b9a90";

                thisURL = BASED_URL + str_cur1 + endURL + keyURL;
                new ConnectTheLib().execute("");
            }
        });
    }

    private static final String BASED_URL =
            "https://api-platform.systran.net/translation/text/translate?input=";


    private class ConnectTheLib extends AsyncTask<String, Void, String> {
        private String datapost = null;
        @Override
        protected String doInBackground(String... params) {
            try {

                URL url = new URL(thisURL);
                HttpURLConnection connection =
                        (HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(1024);
                String tmp="";
                while((tmp=reader.readLine())!=null)
                    json.append(tmp).append("\n");
                reader.close();

                JSONObject data = new JSONObject(json.toString());
                try {
                    JSONArray outputs = (JSONArray)data.get("outputs");
                    datapost = outputs.getJSONObject(0).getString("output");
                }catch (Exception e){               // chac chan rang json co get dc tu server hay ko
                    datapost = data.toString();
                }

                return data.toString();

            }catch(Exception e){
                datapost = "Cannot translate!";
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tv1 = (TextView) findViewById(R.id.tv_cur2_val);
            tv1.setText(datapost);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}




