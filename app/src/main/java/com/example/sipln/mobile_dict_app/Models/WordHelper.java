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

public class WordHelper extends AsyncTask<String, Void, String>{
    private Context context;
    private Word word;

    private final String BASED_URL = "https://api-platform.systran.net/translation/text/translate?input=";
    private final String endURL = "&source=en&target=vi&withSource=false&withAnnotations=false&backTranslation=false&encoding=utf-8";
    private final String keyURL = "&key=81f68939-5120-4774-a06c-aa92e86b9a90";

    private String datapost = "";

    public WordHelper(Context context, Word word) {
        this.context = context;
        this.word = word;
    }

    @Override
    protected String doInBackground(String... strings) {
        String request = BASED_URL + word.getWord() + endURL + keyURL;
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
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        word.setMeaning(datapost);
        createNotification();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void translate(){
        execute("");
    }

    private void createNotification() {
        Intent intent = new Intent(context, NotifyService.class);
        intent.putExtra("word", word.getWord());
        intent.putExtra("meaning", word.getMeaning());
        context.startService(intent);
    }

}
