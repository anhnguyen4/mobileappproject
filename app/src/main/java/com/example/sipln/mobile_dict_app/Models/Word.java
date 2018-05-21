package com.example.sipln.mobile_dict_app.Models;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.util.Log;

import com.example.sipln.mobile_dict_app.Services.NotifyService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Word {

    private Context context;

    private String word;

    private String meaning;


    public Word(Context context, String word) {
        this.context = context;
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void translate() {
        new Translate().execute("");
    }

    private class Translate extends AsyncTask <String, Void, String> {

        private final String BASED_URL = "https://api-platform.systran.net/translation/text/translate?input=";
        private final String endURL = "&source=en&target=vi&withSource=false&withAnnotations=false&backTranslation=false&encoding=utf-8";
        private final String keyURL = "&key=81f68939-5120-4774-a06c-aa92e86b9a90";

        private String datapost = "";

        @Override
        protected String doInBackground(String... strings) {
            String request = BASED_URL + word + endURL + keyURL;
            try {
                URL url = new URL(request);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer json = new StringBuffer(1024);
                String tmp;
                while ((tmp = reader.readLine()) != null) {
                    json.append(tmp + "\\n");
                }
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
                Log.i("Exception", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            meaning = datapost;
            createNotification(meaning);
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    private void createNotification(String meaning) {
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra("meaning", meaning);
        context.startService(intent);
    }
}
