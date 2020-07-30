package com.ammanz.personalmushaf.mushafselector;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.ammanz.personalmushaf.QuranActivity;
import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadataFactory;
import com.ammanz.personalmushaf.navigation.NavigationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.play.core.assetpacks.AssetPackManager;
import com.google.android.play.core.assetpacks.AssetPackManagerFactory;
import com.google.android.play.core.assetpacks.AssetPackStateUpdateListener;
import com.google.android.play.core.assetpacks.AssetPackStates;
import com.google.android.play.core.assetpacks.model.AssetPackStatus;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MushafStyleFragment extends Fragment {
    private boolean fromSettings;
    private AlertDialog downloadConfirmationDialog;
    private AlertDialog.Builder builder;
    private MushafMetadata mushafMetadata;
    private int mushafIndex;

    private ProgressDialog downloadProgressDialog;
    private ProgressDialog unzippingProgressDialog;

    private AssetPackStateUpdateListener assetPackStateUpdateListener;
    private AssetPackManager assetPackManager;
    private QuranSettings quranSettings;

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

        quranSettings = QuranSettings.getInstance();

        mushafIndex = getArguments().getInt("mushaf", QuranSettings.CLASSICMADANI15);

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

        if (quranSettings.isMushafAvailable(mushafIndex)) {
            fab.setImageResource(R.drawable.ic_check_black_24dp);
            fab.setOnClickListener(v1 -> {
                updateQuranSettings();
                if (!fromSettings)
                    startActivity(new Intent(getContext(), QuranActivity.class));
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
        setupAssetPackStateUpdateListener();
        List<String> assetPackName = new ArrayList<>();
        assetPackName.add(mushafMetadata.getId());
        Task<AssetPackStates> task = assetPackManager.fetch(assetPackName);
        task.addOnFailureListener(e -> {
            System.out.println(e.getMessage());
        });
    }

    private StorageReference getZipStorageReference() {
        return null;
        /*return FirebaseStorage.getInstance().getReference()
                .child(mushafMetadata.getAssetName() + ".zip");*/
    }


    private void updateQuranSettings() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref.edit().putBoolean("firststart", false).apply();
        pref.edit().putString("mushaf", Integer.toString(mushafIndex)).apply();
        quranSettings.updateAvailableMushafs(getContext());
        quranSettings.setMushafVersion(mushafIndex);
        quranSettings.setMushafMetadata(mushafIndex);
    }

    private void setupAssetPackStateUpdateListener() {
        assetPackManager = AssetPackManagerFactory.getInstance(getContext());
        assetPackStateUpdateListener = assetPackState -> {
            switch (assetPackState.status()) {
                case AssetPackStatus.PENDING:
                    Log.i(TAG, "Pending");
                    break;

                case AssetPackStatus.DOWNLOADING:
                    int prevProgress = downloadProgressDialog.getProgress();
                    long downloaded = assetPackState.bytesDownloaded();
                    long totalSize = assetPackState.totalBytesToDownload();
                    double percent = 100.0 * downloaded / totalSize;

                    Log.i(TAG, "PercentDone=" + String.format("%.2f", percent));
                    downloadProgressDialog.incrementProgressBy((int) (percent - prevProgress));
                    break;

                case AssetPackStatus.TRANSFERRING:
                    // 100% downloaded and assets are being transferred.
                    // Notify user to wait until transfer is complete.
                    downloadProgressDialog.hide();
                    unzippingProgressDialog.show();
                    break;

                case AssetPackStatus.COMPLETED:
                    updateQuranSettings();
                    unzippingProgressDialog.hide();
                    assetPackManager.unregisterListener(assetPackStateUpdateListener);
                    if (!fromSettings)
                        startActivity(new Intent(getContext(), NavigationActivity.class));
                    getActivity().finishAffinity();
                    break;

                case AssetPackStatus.FAILED:
                    // Request failed. Notify user.
                    Log.e(TAG, String.valueOf(assetPackState.errorCode()));
                    break;

                case AssetPackStatus.CANCELED:
                    // Request canceled. Notify user.
                    break;

                case AssetPackStatus.WAITING_FOR_WIFI:
                    break;

                case AssetPackStatus.NOT_INSTALLED:
                    // Asset pack is not downloaded yet.
                    break;
            }
        };
        assetPackManager.registerListener(assetPackStateUpdateListener);
    }
}
