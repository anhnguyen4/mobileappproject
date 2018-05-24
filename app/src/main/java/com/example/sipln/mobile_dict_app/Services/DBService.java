package com.example.sipln.mobile_dict_app.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.sipln.mobile_dict_app.Models.Word;
import com.example.sipln.mobile_dict_app.Models.WordHelper;
import com.example.sipln.mobile_dict_app.Views.Activities.HomeActivity;
import com.google.gson.Gson;

import java.lang.invoke.WrongMethodTypeException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBService extends IntentService {


    private String SHARED_PREFERENCES_NAME = "recent_words";
    private String KEY_WORD = "Word_";
    private String COUNT = "COUNT";
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
            if(action.equals("Save"+intent.getExtras().getString("pos"))) {
                SaveToDB(intent);
                List<Word> wordList = LoadFromDB();
                Intent updateUI_intent = new Intent();
                String data = gson.toJson(wordList);
                updateUI_intent.putExtra("data", data);
                updateUI_intent.setAction("Update_UI");
                LocalBroadcastManager.getInstance(this).sendBroadcast(updateUI_intent);
            }
        }
    }

    private void SaveToDB(Intent intent){

        recentWords = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();
        Bundle receivedData = intent.getExtras();
        if (receivedData != null) {
            Word word = new Word(receivedData.getString("word"), receivedData.getString("meaning"));

            gson = new Gson();
            String data = gson.toJson(word);

            int top = getTop();

            editor.putInt(COUNT, top);
            editor.putString(KEY_WORD + setNum(top), data);
            editor.commit();
        }
        else {
            Log.i("Save Error", "Bundle is NULL");
        }

    }

    private List<Word> LoadFromDB(){

        recentWords = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        List<Word> wordList = new ArrayList<>();

//        Map<String, ?> allEntries = recentWords.getAll();
////        for (Map.Entry<String, ?> mEntries : allEntries.entrySet()) {
////            if (!mEntries.getKey().equals(COUNT)) {
////                gson = new Gson();
////                Word word = gson.fromJson(mEntries.getValue().toString(), Word.class);
////                Log.i(word.getWord(), word.getMeaning());
////                wordList.add(word);
////            }
////        }

        int num = getTop();
        gson = new Gson();
        for (int i = 0; i < num; i++) {
            Word word =   gson.fromJson(recentWords.getString(KEY_WORD + setNum(i), "0"), Word.class);
            wordList.add(word);
        }

        return wordList;
    }

    public void ClearDB() {
        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();
        editor.clear();
        editor.commit();
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
