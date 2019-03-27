package com.example.personalmushaf.navigation;

import android.content.Intent;
import android.os.Bundle;

import com.example.personalmushaf.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class NavigationActivity extends AppCompatActivity {

    public JuzAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        String[] data;
        int type;

        Intent intent = getIntent();

        if (intent.getIntExtra("new page number", 0) != -1) {
            data = getResources().getStringArray(R.array.juzz_descriptions);
            type = 0;
        }
        else {
            data = getResources().getStringArray(R.array.juzz_one_content);
            type = -1;
        }

        adapter = new JuzAdapter(data, type);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


}
