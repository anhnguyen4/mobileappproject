package a5mobiledevs.ex3;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBService extends IntentService {

    private Context context = this;
    private String SHARED_PREFERENCES_NAME = "JobsInfo";
    private String KEY_WORD = "Jobs_";
    private String COUNT = "COUNT";
    private int ZERO = 0;

    private SharedPreferences recentDatas;
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
                    break;
                case "LOAD":
                    List<String> datas = LoadFromDB();

                    Intent update_saved = new Intent();
                    update_saved.setAction("Update_saved");
                    update_saved.putExtra("data", new Gson().toJson(datas));
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                    break;
                default:
                        break;
            }

        }
    }

    private void SaveToDB(Intent intent){
        Log.i("SavedToDB","SavedToDB");
        recentDatas = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentDatas.edit();
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
        Log.i("LoadFromDB","LoadFromDB");
        recentDatas = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        List<String> datas = new ArrayList<>();

        int num = getTop();
        gson = new Gson();
        for (int i = 0; i < num; i++) {
            String data =   gson.fromJson(recentDatas.getString(KEY_WORD + setNum(i), "0"), String.class);
            datas.add(data);
        }

        return datas;
    }

    private void ClearDB() {
        recentDatas = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentDatas.edit();
        editor.clear();
        editor.commit();
    }

    private int getTop() {
        recentDatas = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        if (!recentDatas.contains(COUNT)) {
            return ZERO;
        } else {
            return recentDatas.getInt(COUNT, ZERO) + 1;
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
