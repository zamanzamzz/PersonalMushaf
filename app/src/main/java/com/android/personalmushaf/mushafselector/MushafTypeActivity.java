package com.android.personalmushaf.mushafselector;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.navigation.NavigationActivity;

import org.jetbrains.annotations.NotNull;


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
    }
}


