package com.android.personalmushaf.mushafselector;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.navigation.NavigationActivity;
import com.android.personalmushaf.util.FileUtils;
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

    private ProgressDialog progressDialog;

    private DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                downloadConfirmationDialog.hide();
                setupProgressDialog();
                Observable.fromCallable(() -> {
                    int max = (int) mushafMetadata.getDownloadSize();
                    int currentProgress = 0;
                    while (currentProgress < max) {
                        currentProgress += 1;
                        progressDialog.incrementProgressBy(1);
                        Thread.sleep(200);
                    }
                    return true;
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
                    // progressDialog.hide();
                });
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

        final int mushafIndex = getArguments().getInt("mushaf", QuranSettings.CLASSIC_MADANI_15_LINE);

        fromSettings = getArguments().getBoolean("from_settings", false);

        mushafMetadata = MushafMetadataFactory.getMushafMetadata(mushafIndex);

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
                updateQuranSettings(mushafIndex);
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

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Downloading " + mushafMetadata.getName());
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

    private void downloadAndUnpackZip(String mushafDirectory, final int mushafIndex, final ProgressBar progressBar) {
        StorageReference pathReference = getZipStorageReference(mushafDirectory);
        final File targetDir = new File(FileUtils.ASSETSDIRECTORY);
        FileUtils.validateDirectory(targetDir);
        final File zipFile = new File(FileUtils.ASSETSDIRECTORY + "/" + mushafDirectory + ".zip");
        FileDownloadTask task = pathReference.getFile(zipFile);
        task.addOnProgressListener(
                taskSnapshot -> progressBar.setProgress((int) taskSnapshot.getBytesTransferred()));
        task.addOnSuccessListener(taskSnapshot -> {
            try {
                FileUtils.unzip(zipFile, targetDir, progressBar);
            } catch (IOException e) {
            } finally {
                updateQuranSettings(mushafIndex);
                zipFile.delete();
                if (!fromSettings)
                    startActivity(new Intent(getContext(), NavigationActivity.class));
                getActivity().finishAffinity();
            }
        });
        task.addOnFailureListener(e -> {});
    }

    private StorageReference getZipStorageReference(String mushafDirectory) {
        return FirebaseStorage.getInstance().getReference()
                .child(mushafDirectory + ".zip");
    }


    private void updateQuranSettings(int mushafIndex) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref.edit().putBoolean("firststart", false).apply();
        pref.edit().putString("mushaf", Integer.toString(mushafIndex)).apply();
        QuranSettings.getInstance().setMushafVersion(mushafIndex);
        QuranSettings.getInstance().setMushafMetadata(mushafIndex);
    }
}
