package com.example.sipln.mobile_dict_app.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.sipln.mobile_dict_app.Models.Word;
import com.example.sipln.mobile_dict_app.Models.WordHelper;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class DBService extends IntentService {


    private String SHARED_PREFERENCES_NAME = "recent_words";
    private String KEY_WORD = "key_word_";
    private String COUNT = "count";
    private int ZERO = 0;

    private SharedPreferences recentWords;
    private SharedPreferences.Editor editor;

    private Gson gson;

    public DBService() {
        super("DBService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(action.equals("Save")) {
                Log.i("onHandleIntent", intent.getExtras().getString("word"));
                Log.i("onHandleIntent", intent.getExtras().getString("meaning"));
                SaveToDB(intent);
            }

        }
    }

    private void SaveToDB(Intent intent){
        Log.i("Save", "Save to DB");
        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();

        Word word = new Word(intent.getExtras().getString("word"), intent.getExtras().getString("meaning"));
        Log.i("SaveToDB", word.toString());
        gson = new Gson();
        String data = gson.toJson(word);

        int top = getTop();

        editor.putInt(COUNT, top);
        editor.putString(KEY_WORD + setNum(top), data);
        editor.apply();

    }

    private void LoadFromDB(){

    }

    public int getTop() {
        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        if (!recentWords.contains(COUNT)) {
            return ZERO;
        } else {
            return recentWords.getInt(COUNT, ZERO) + 1;
        }

    }


    public String setNum(int num) {

        DecimalFormat pattern = new DecimalFormat( "00000000" );

        return String.valueOf(pattern.format(num));

    }

}
