package com.android.personalmushaf.startup;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.model.mushafs.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.model.mushafs.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.navigation.NavigationActivity;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;
import com.android.personalmushaf.util.FileUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class StartupFragment extends Fragment {
    public StartupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_startup, container, false);

        final int mushafIndex = getArguments().getInt("mushaf", QuranSettings.MADANI15LINE);

        final MushafMetadata mushafMetadata = MushafMetadataFactory.getMushafMetadata(mushafIndex);

        ImageView imageView = v.findViewById(R.id.page1);
        TextView title = v.findViewById(R.id.mushafStyleTitle);
        TextView description = v.findViewById(R.id.mushafStyleDescription);

        title.setText(mushafMetadata.getName());
        description.setText(mushafMetadata.getDescription());

        imageView.setImageDrawable(v.getResources().getDrawable(mushafMetadata.getPreviewDrawableIDs()[0], null));

        final ProgressBar progressBar = v.findViewById(R.id.progress_horizontal);
        final Window window = getActivity().getWindow();
        v.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (QuranSettings.getInstance().isMushafAvailable(mushafIndex)) {
                    updateQuranSettings(mushafIndex);
                    startActivity(new Intent(getContext(), NavigationActivity.class));
                    getActivity().finishAffinity();
                } else {
                    downloadAndUnpackZip(mushafMetadata.getDirectoryName(), mushafIndex, window, progressBar);
                }
            }
        });
        return v;
    }

    private void downloadAndUnpackZip(String mushafDirectory, final int mushafIndex, final Window window, final ProgressBar progressBar) {
        setupUIForDownload(window, progressBar);
        StorageReference pathReference = getZipStorageReference(mushafDirectory);
        final File targetDir = new File(QuranConstants.ASSETSDIRECTORY);
        FileUtils.validateDirectory(targetDir);
        final File zipFile = new File(QuranConstants.ASSETSDIRECTORY + "/" + mushafDirectory + ".zip");
        FileDownloadTask task = pathReference.getFile(zipFile);
        progressBar.setMax((int) task.getSnapshot().getTotalByteCount());
        task.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                try {
                    FileUtils.unzip(zipFile, targetDir, progressBar);
                } catch (IOException e) {
                } finally {
                    resetUI(window, progressBar);
                    updateQuranSettings(mushafIndex);
                    zipFile.delete();
                    startActivity(new Intent(getContext(), NavigationActivity.class));
                    getActivity().finishAffinity();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                resetUI(window, progressBar);
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull FileDownloadTask.TaskSnapshot taskSnapshot) {
                progressBar.setProgress((int) taskSnapshot.getBytesTransferred());
            }
        });
    }

    private StorageReference getZipStorageReference(String mushafDirectory) {
        return FirebaseStorage.getInstance().getReference()
                .child(mushafDirectory + "/" + mushafDirectory + ".zip");
    }

    private void setupUIForDownload(Window window, ProgressBar progressBar) {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void resetUI(Window window, ProgressBar progressBar) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        progressBar.setVisibility(View.GONE);
    }

    private void updateQuranSettings(int mushafIndex) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        pref.edit().putBoolean("firststart", false).apply();
        pref.edit().putString("mushaf", Integer.toString(mushafIndex)).apply();
    }
}
