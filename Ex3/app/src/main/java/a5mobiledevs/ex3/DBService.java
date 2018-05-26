package a5mobiledevs.ex3;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBService extends IntentService {

    private String SHARED_PREFERENCES_NAME = "JobsInfo";
    private String KEY_WORD = "Jobs_";
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

            switch (action){
                case "SAVE":
                    Log.i("SAVE", "SAVE");
                    SaveToDB(intent);
                case "LOAD":
                    LoadFromDB();

            }

        }
    }

    private void SaveToDB(Intent intent){

        recentWords = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();
        Bundle receivedData = intent.getExtras();
        if (receivedData != null) {
            String word = receivedData.getString("data");
            if(!hasExisted(word, LoadFromDB())) {
                gson = new Gson();
                String data = gson.toJson(word);

                int top = getTop();

                editor.putInt(COUNT, top);
                editor.putString(KEY_WORD + setNum(top), data);
                editor.commit();
            }
        }
        else {
            Log.i("Save Error", "Bundle is NULL");
        }

    }

    private List<String> LoadFromDB(){

        recentWords = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        List<String> wordList = new ArrayList<>();

        int num = getTop();
        gson = new Gson();
        for (int i = 0; i < num; i++) {
            String word =   gson.fromJson(recentWords.getString(KEY_WORD + setNum(i), "0"), String.class);
            wordList.add(word);
        }

        return wordList;
    }

    private void ClearDB() {
        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentWords.edit();
        editor.clear();
        editor.commit();
    }

    private int getTop() {
        recentWords = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        if (!recentWords.contains(COUNT)) {
            return ZERO;
        } else {
            return recentWords.getInt(COUNT, ZERO) + 1;
        }

    }

    private String setNum(int num) {
        DecimalFormat pattern = new DecimalFormat( "00000000" );
        return String.valueOf(pattern.format(num));
    }

    private boolean hasExisted(String word, List<String> wordList){
        for (int i =0; i < wordList.size(); i++){
            if (word.equals(wordList.get(i))) {
                return true;
            }
        }
        return false;
    }

}
