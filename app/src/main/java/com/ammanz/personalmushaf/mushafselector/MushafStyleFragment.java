package com.ammanz.personalmushaf.mushafselector;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadataFactory;
import com.ammanz.personalmushaf.navigation.NavigationActivity;
import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.util.FileUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MushafStyleFragment extends Fragment {
    private boolean fromSettings;
    private AlertDialog downloadConfirmationDialog;
    private AlertDialog.Builder builder;
    private MushafMetadata mushafMetadata;
    private int mushafIndex;

    private ProgressDialog downloadProgressDialog;
    private ProgressDialog unzippingProgressDialog;

    private DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                downloadConfirmationDialog.hide();
                downloadProgressDialog.show();
                downloadAndUnpackZip();
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                downloadConfirmationDialog.hide();
                break;
        }
    };

    public MushafStyleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_startup, container, false);

        mushafIndex = getArguments().getInt("mushaf", QuranSettings.CLASSIC_MADANI_15_LINE);

        fromSettings = getArguments().getBoolean("from_settings", false);

        mushafMetadata = MushafMetadataFactory.getMushafMetadata(mushafIndex);

        setupProgressDialogForDownload();

        setupProgressDialogForUnzipping();

        ImageView imageView = v.findViewById(R.id.page1);
        TextView title = v.findViewById(R.id.mushafStyleTitle);
        TextView downloadSize = v.findViewById(R.id.mushafDownloadSize);
        TextView description = v.findViewById(R.id.mushafStyleDescription);

        title.setText(mushafMetadata.getName());
        downloadSize.setText(String.format( "Download size: %.2f MB" ,mushafMetadata.getDownloadSize()));
        description.setText(mushafMetadata.getDescription());

        imageView.setImageDrawable(v.getResources().getDrawable(mushafMetadata.getPreviewDrawableIDs()[0], null));

        FloatingActionButton fab = v.findViewById(R.id.fab);

        if (QuranSettings.getInstance().isMushafAvailable(mushafIndex)) {
            fab.setImageResource(R.drawable.ic_check_black_24dp);
            fab.setOnClickListener(v1 -> {
                updateQuranSettings();
                if (!fromSettings)
                    startActivity(new Intent(getContext(), NavigationActivity.class));
                getActivity().finishAffinity();
            });
        } else {
            fab.setImageResource(R.drawable.ic_file_download_black_24dp);
            fab.setOnClickListener(v1 -> {
                showConfirmationDialog();
            });
        }

        return v;
    }

    private void showConfirmationDialog() {
        builder = new AlertDialog.Builder(getContext());

        builder.setCancelable(true);
        builder.setTitle("Download confirmation");
        builder.setMessage(String.format("%.2f MB will need to be downloaded to use this mushaf." +
                " Would you like to download?", mushafMetadata.getDownloadSize()));
        builder.setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener);
        downloadConfirmationDialog = builder.create();

        downloadConfirmationDialog.show();
    }

    private void setupProgressDialogForDownload() {
        downloadProgressDialog = new ProgressDialog(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        downloadProgressDialog.setCancelable(false);
        downloadProgressDialog.setMessage("Downloading " + mushafMetadata.getName());
        downloadProgressDialog.setMax(100);
        downloadProgressDialog.setProgress(0);
        downloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downloadProgressDialog.setIndeterminate(false);
    }

    private void setupProgressDialogForUnzipping() {
        unzippingProgressDialog = new ProgressDialog(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        unzippingProgressDialog.setCancelable(false);
        unzippingProgressDialog.setMessage("Processing files");
        unzippingProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        unzippingProgressDialog.setIndeterminate(true);
    }

    private void downloadAndUnpackZip() {
        StorageReference pathReference = getZipStorageReference();
        final File targetDir = new File(FileUtils.ASSETSDIRECTORY);
        FileUtils.validateDirectory(targetDir);
        final File zipFile = new File(FileUtils.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + ".zip");
        FileDownloadTask task = pathReference.getFile(zipFile);
        task.addOnProgressListener(
                taskSnapshot -> {
                        int prevProgress = downloadProgressDialog.getProgress();
                        int currentProgress = (int) ((taskSnapshot.getBytesTransferred() / (mushafMetadata.getDownloadSize() * Math.pow(10.0, 6.0))) * 100.0);
                        downloadProgressDialog.incrementProgressBy(currentProgress - prevProgress);
                });
        task.addOnSuccessListener(taskSnapshot -> {
                downloadProgressDialog.hide();
                unzippingProgressDialog.show();
                Observable.fromCallable(() -> {
                    try {
                        FileUtils.unzip(zipFile, targetDir);
                        zipFile.delete();
                    } catch (IOException e) {
                        return false;
                    }
                    return true;
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
                    if (result) {
                        updateQuranSettings();
                        unzippingProgressDialog.hide();
                        if (!fromSettings)
                            startActivity(new Intent(getContext(), NavigationActivity.class));
                        getActivity().finishAffinity();
                    }
                });
            });
    }

    private StorageReference getZipStorageReference() {
        return FirebaseStorage.getInstance().getReference()
                .child(mushafMetadata.getDirectoryName() + ".zip");
    }


    private void updateQuranSettings() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref.edit().putBoolean("firststart", false).apply();
        pref.edit().putString("mushaf", Integer.toString(mushafIndex)).apply();
        QuranSettings.getInstance().setMushafVersion(mushafIndex);
        QuranSettings.getInstance().setMushafMetadata(mushafIndex);
    }
}
