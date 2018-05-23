package com.example.sipln.mobile_dict_app.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.sipln.mobile_dict_app.Models.Word;
import com.example.sipln.mobile_dict_app.Models.WordHelper;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.Map;

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
                SaveToDB(intent);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            }

        }
    }

    private void SaveToDB(Intent intent){

        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();
        Bundle receivedData = new Bundle();
        if (receivedData != null) {
            Word word = new Word(receivedData.getString("word"), receivedData.getString("meaning"));

            gson = new Gson();
            String data = gson.toJson(word);

            int top = getTop();

            editor.putInt(COUNT, top);
            editor.putString(KEY_WORD + setNum(top), data);
            editor.apply();
        }
        else {
            Log.i("Save Error", "Bundle is NULL");
        }

    }

    private void LoadFromDB(){
//        int pos, length;
//        //Contain list all recent saved words-meaning and sorted with time order
//        String[][] mListWord;
//        //String which show on screen
//        StringBuffer result = new StringBuffer("");
//
//
//        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
//
//        length = getTop();
//        mListWord = new String[2][length];
//        Map<String, ?> allEntries = recentWords.getAll();
//        for (Map.Entry<String, ?> mEntries : allEntries.entrySet()) {
//            if (!mEntries.getKey().equals(COUNT)) {
//                gson = new Gson();
//                word = gson.fromJson(mEntries.getValue().toString(), Word.class);
//                pos = Integer.parseInt(mEntries.getKey().substring(9));
//                mListWord[0][pos] = word.getKeyword();
//                mListWord[1][pos] = word.getMeaning();
//            }
//        }
    }

    public void ClearDB() {
        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();
        editor.clear();
        editor.apply();
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
