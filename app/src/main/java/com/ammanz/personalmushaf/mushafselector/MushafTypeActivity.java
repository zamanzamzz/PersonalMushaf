package com.ammanz.personalmushaf.mushafselector;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ammanz.personalmushaf.R;


public class MushafTypeActivity extends AppCompatActivity {
    private static final int  REQUEST_PERMISSION_CODE = 1111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mushaftype);

        boolean fromSettings = getIntent().getBooleanExtra("from_settings", false);

        RecyclerView recyclerView = findViewById(R.id.mushaf_type_recyclerview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        MushafTypeAdapter adapter = new MushafTypeAdapter(fromSettings);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        ((TextView) findViewById(R.id.dialog_textview)).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}


