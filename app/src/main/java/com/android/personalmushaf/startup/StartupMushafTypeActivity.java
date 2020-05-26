package com.android.personalmushaf.startup;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
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

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.navigation.NavigationActivity;

import org.jetbrains.annotations.NotNull;


public class StartupMushafTypeActivity extends AppCompatActivity {
    private AlertDialog mushafTypeDialog;
    private static final int  REQUEST_PERMISSION_CODE = 1111;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        if (checkPermission())
            setMushafType();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mushafTypeDialog != null)
            mushafTypeDialog.show();
    }

    private void setMushafType() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        if(!isAnyMushafAvailable() || pref.getBoolean("firststart", true)){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("Choose mushaf type");

            String[] mushafTypes = {"13 Lines", "15 Lines"};
            builder.setItems(mushafTypes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent chooseMushafStyle = new Intent(getApplicationContext(), StartupMushafStyleActivity.class);
                    chooseMushafStyle.putExtra("mushaf_type", which);
                    startActivity(chooseMushafStyle);
                }
            });

            mushafTypeDialog = builder.create();
            mushafTypeDialog.show();
        } else {
            startActivity(new Intent(this, NavigationActivity.class));
            finish();
        }
    }

    private boolean isAnyMushafAvailable() {
        return QuranSettings.getInstance().updateAvailableMushafs();
    }

    private boolean checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                + ContextCompat.checkSelfPermission(
                this ,Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(
                this ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this ,Manifest.permission.INTERNET)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this ,Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this ,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                // If we should give explanation of requested permissions

                final Activity that = this;
                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Internet, Read and Write External" +
                        " Storage permissions are required.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                that,
                                new String[]{
                                        Manifest.permission.INTERNET,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                },
                                REQUEST_PERMISSION_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.INTERNET,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        },
                        REQUEST_PERMISSION_CODE
                );
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults){
        switch (requestCode){
            case REQUEST_PERMISSION_CODE:{
                // When request is cancelled, the results array are empty
                if (
                        (grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    setMushafType();
                } else {
                    checkPermission();
                }
            }
        }
    }
}


