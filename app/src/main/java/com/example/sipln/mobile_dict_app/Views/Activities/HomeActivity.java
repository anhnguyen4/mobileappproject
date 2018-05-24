package com.example.sipln.mobile_dict_app.Views.Activities;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.content.IntentFilter;
        import android.support.annotation.NonNull;
        import android.support.design.widget.NavigationView;
        import android.support.v4.content.LocalBroadcastManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;

        import android.util.Log;
        import android.view.MenuItem;

        import com.example.sipln.mobile_dict_app.R;

        import java.util.ArrayList;
        import java.util.List;

        import com.example.sipln.mobile_dict_app.Models.Word;
        import com.example.sipln.mobile_dict_app.Presenters.RVWordEntryAdapter;
        import com.example.sipln.mobile_dict_app.Services.ClipboardObserveService;
        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

public class HomeActivity extends AppCompatActivity {

    private List<Word> wordList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RVWordEntryAdapter rvWordEntryAdapter;

    private boolean receiverIsRegisted = false;
    private HomeBroadcastReceiver receiver = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if ( !receiverIsRegisted) {
            receiver = new HomeBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("Update_UI");
            LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
            receiverIsRegisted = true;
        }


        NavigationView drawer = findViewById(R.id.nv_drawer);
        drawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.About:
                        Intent about = new Intent(HomeActivity.this, AboutActivity.class);
                        startActivity(about);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.rv_word_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rvWordEntryAdapter = new RVWordEntryAdapter(wordList);
        recyclerView.setAdapter(rvWordEntryAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent clipboardObserver = new Intent(this, ClipboardObserveService.class);
        startService(clipboardObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(receiverIsRegisted) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
            receiver = null;
            receiverIsRegisted = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    private  class HomeBroadcastReceiver extends  BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("Update_UI")) {
                updateUI(intent);
            }
        }
    }

    private void updateUI(Intent intent) {
        Gson gson = new Gson();
        String data = intent.getExtras().getString("data");
        wordList = gson.fromJson(data, new TypeToken<List<Word>>(){}.getType());
        recyclerView.setAdapter(new RVWordEntryAdapter(wordList));
        recyclerView.invalidate();
    }

}
