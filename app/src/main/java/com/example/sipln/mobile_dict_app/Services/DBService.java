package com.example.sipln.mobile_dict_app.Services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.sipln.mobile_dict_app.Models.WordHelper;
import com.google.gson.Gson;

public class DBService extends IntentService {

    private WordHelper wordHelper;

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

    public DBService(String name, WordHelper wordHelper) {
        super(name);
        this.wordHelper = wordHelper;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(action.equals("Save")) {
                SaveToDB();
            }

        }
    }

    private void SaveToDB(){
        Log.i("Save", "Save to DB");
    }

    private void LoadFromDB(){

    }

}
