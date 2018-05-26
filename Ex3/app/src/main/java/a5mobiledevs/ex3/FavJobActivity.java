package a5mobiledevs.ex3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<String> favJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_job);

        recyclerView = findViewById(R.id.rv_favjobs);

        favJobs = new ArrayList<>();

        recyclerViewAdapter = new RecyclerViewAdapter(context, favJobs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        Intent loadDB = new Intent(this, DBService.class);
        loadDB.setAction("LOAD");
        startService(loadDB);
    }

    protected class SavedTabBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("Update_saved"))
                Update_UI(intent);
        }
    }

    private void Update_UI(Intent intent) {
        Log.i("Update_UI", "Update_saved");
        String receivedData = intent.getExtras().toString();
        favJobs = new Gson().fromJson(receivedData, new TypeToken<List<String>>(){}.getType());
        recyclerView.setAdapter(new RecyclerViewAdapter(context, favJobs));
        recyclerView.invalidate();

    }

}
