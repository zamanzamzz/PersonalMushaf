package com.ammanz.personalmushaf;

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

import com.ammanz.personalmushaf.mushafselector.MushafTypeActivity;
import com.ammanz.personalmushaf.navigation.NavigationActivity;

import org.jetbrains.annotations.NotNull;


public class StartupActivity extends AppCompatActivity {
    private QuranSettings quranSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mushaftype);
        quranSettings = QuranSettings.getInstance();
        quranSettings.initializeAvailableMushafs(getApplicationContext());
        checkDataFiles();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void checkDataFiles() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        if (!isAnyMushafAvailable() || pref.getBoolean("firststart", true)){
            startActivity(new Intent(this, MushafTypeActivity.class));
        } else {
            startActivity(new Intent(this, NavigationActivity.class));
        }
        finish();
    }

    private boolean isAnyMushafAvailable() {
        quranSettings.updateAvailableMushafs(getApplicationContext());
        return quranSettings.isAnyMushafAvailable();
    }
}


