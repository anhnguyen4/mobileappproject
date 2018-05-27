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

public class DBService extends IntentService {

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

    public static void SaveToDB(Context context, String data){
        Intent saveDB = new Intent(context, DBService.class);
        saveDB.setAction("SAVE");
        saveDB.putExtra("data", data);
        context.startService(saveDB);
        Log.e("lkkssdjf", "lkajddf");
    }

    public static void LoadFromDB(Context context){
        Intent loadDB = new Intent(context, DBService.class);
        loadDB.setAction("LOAD");
//        loadDB.putExtra("","");
        context.startService(loadDB);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("onHandleIntent","....");
        if (intent != null) {
            final String action = intent.getAction();
            switch (action){
                case "SAVE":
                        Log.e("SAVE", "Ma no chu");
                        SaveToDB(intent);
                        Log.e("^^^", "$$$$");
                        break;
                case "LOAD":
                        Log.e("LOAD", "dcmn");
                        List<String> datas = LoadFromDB();
                        Intent update_saved = new Intent();
                        update_saved.setAction("Update_saved");
                        update_saved.putExtra("data", new Gson().toJson(datas));
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(update_saved);
                        break;
                default:
                        Log.e("onHandleIntent","");
                        break;
            }
        }
    }

    private void SaveToDB(Intent intent){
        recentDatas = getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = recentDatas.edit();
        Bundle receivedData = intent.getExtras();
        if (receivedData != null) {
            String word = receivedData.getString("data");
            if(!hasExisted(word, LoadFromDB())) {
                gson = new Gson();
                String data = gson.toJson(word);
                Log.e("data", data);
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
        Log.e("LoadFromDB", "");
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

    private boolean hasExisted(String word, List<String> data){
        for (int i =0; i < data.size(); i++){
            if (word.equals(data.get(i))) {
                return true;
            }
        }
        return false;
    }

}
