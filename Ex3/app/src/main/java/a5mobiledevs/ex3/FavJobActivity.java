package a5mobiledevs.ex3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FavJobActivity extends AppCompatActivity {

    private Context context = this;
    private RecyclerView recyclerView;
    private FavRecyclerViewAdapter favRecyclerViewAdapter;
    private List<String> favJobs = new ArrayList<>();

    private  boolean receiverIsRegisted;
    private SavedTabBroadCastReceiver savedTabBroadCastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_job);

        recyclerView = findViewById(R.id.rv_favjobs);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        favRecyclerViewAdapter = new FavRecyclerViewAdapter(context, favJobs);
        recyclerView.setAdapter(favRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (!receiverIsRegisted) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("Update_saved");
            savedTabBroadCastReceiver = new SavedTabBroadCastReceiver();
            LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(savedTabBroadCastReceiver, filter);
            receiverIsRegisted = true;
        }

        DBService.LoadFromDB(context);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(receiverIsRegisted) {
            LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(savedTabBroadCastReceiver);
            savedTabBroadCastReceiver = null;
            receiverIsRegisted = false;
        }
    }

    private class SavedTabBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("Update_saved"))
                Log.e("Broadcast", "....");
                Update_UI(intent);
        }
    }

    private void Update_UI(Intent intent) {
        String receivedData = intent.getExtras().getString("data");
        favJobs = new Gson().fromJson(receivedData, new TypeToken<List<String>>(){}.getType());
        recyclerView.setAdapter(new FavRecyclerViewAdapter(context, favJobs));
        recyclerView.invalidate();
    }

}
