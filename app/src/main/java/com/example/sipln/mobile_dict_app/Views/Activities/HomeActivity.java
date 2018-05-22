package com.example.sipln.mobile_dict_app.Views.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.MenuItem;

import com.example.sipln.mobile_dict_app.R;

import java.util.ArrayList;
import java.util.List;

import com.example.sipln.mobile_dict_app.Models.Word;
import com.example.sipln.mobile_dict_app.Presenters.RVWordEntryAdapter;
import com.example.sipln.mobile_dict_app.Services.ClipboardObserveService;

public class HomeActivity extends AppCompatActivity {

    private List<Word> wordList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RVWordEntryAdapter rvWordEntryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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


        initData();
        rvWordEntryAdapter = new RVWordEntryAdapter(wordList);
        recyclerView.setAdapter(rvWordEntryAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Intent clipboardObserver = new Intent(this, ClipboardObserveService.class);
        startService(clipboardObserver);
    }

    private void initData(){
        wordList = new ArrayList<>();
        wordList.add(new Word("Hello"));
        wordList.add(new Word("Happy"));
        wordList.add(new Word("Family"));
    }

}
