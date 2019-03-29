package com.example.personalmushaf.navigation;

import android.content.Intent;
import android.os.Bundle;

import com.example.personalmushaf.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class NavigationActivity extends AppCompatActivity {

    public JuzAdapter adapter;
    Toolbar navigationToolber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navigationToolber = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(navigationToolber);
        ActionBar actionBar = getSupportActionBar();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        String[] data;
        int type;

        Intent intent = getIntent();

        int receivingType = intent.getIntExtra("type", 0);
        int juzNumber = intent.getIntExtra("juz number", 0);

        if (receivingType == 0) {
            data = getResources().getStringArray(R.array.juz_descriptions);
            actionBar.hide();
            type = 0;
        } else if (receivingType == 1) {
            data = getResources().getStringArray(R.array.juz_content);
            actionBar.show();
            actionBar.setTitle("Chapter " + Integer.toString(juzNumber));
            type = 1;
        } else {
            data = (QuranPageData.getInstance().RukuContentTitles)[juzNumber-1];
            actionBar.show();
            actionBar.setTitle("Chapter " + Integer.toString(juzNumber));
            type = 2;
        }

        adapter = new JuzAdapter(data, type, juzNumber);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
