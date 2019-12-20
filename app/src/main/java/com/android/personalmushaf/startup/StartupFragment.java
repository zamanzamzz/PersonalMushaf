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
import com.android.personalmushaf.navigation.NavigationActivity;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;
import com.android.personalmushaf.util.FileUtils;
import com.android.personalmushaf.widgets.QuranSinglePage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class StartupFragment extends Fragment {
    public StartupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_startup, container, false);

        int drawableID = getArguments().getInt("drawable_id");
        final String mushafDirectory = getArguments().getString("mushaf_directory");
        String mushafStyleTitle = getArguments().getString("mushaf_style_title");
        String mushafStyleDescription = getArguments().getString("mushaf_style_description");

        ImageView imageView = v.findViewById(R.id.page1);
        TextView title = v.findViewById(R.id.mushafStyleTitle);
        TextView description = v.findViewById(R.id.mushafStyleDescription);

        title.setText(mushafStyleTitle);
        description.setText(mushafStyleDescription);

        imageView.setImageDrawable(v.getResources().getDrawable(drawableID, null));

        final ProgressBar progressBar = v.findViewById(R.id.progress_horizontal);
        final Window window = getActivity().getWindow();
        v.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressBar.setVisibility(View.VISIBLE);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference pathReference = storage.getReference().child(mushafDirectory + "/" + mushafDirectory + ".zip");
                progressBar.setMax(100);
                File root = new File(QuranConstants.ASSETSDIRECTORY);
                if (!root.exists())
                    root.mkdir();
                else if (!root.isDirectory()){
                    root.delete();
                    root.mkdir();
                }
                final File file = new File(QuranConstants.ASSETSDIRECTORY + "/" + mushafDirectory + ".zip");
                pathReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        File targetDir = new File(QuranConstants.ASSETSDIRECTORY);
                        targetDir.mkdir();
                        try {
                            FileUtils.unzip(file, targetDir);
                        } catch (IOException e) {
                        } finally {
                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
                            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            pref.edit().putBoolean("firststart", false).apply();
                            pref.edit().putString("mushaf", "0").apply();
                            file.delete();
                            startActivity(new Intent(getContext(), NavigationActivity.class));
                            getActivity().finishAffinity();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull FileDownloadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setMax((int) taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int) taskSnapshot.getBytesTransferred());
                    }
                });
            }
        });
        return v;
    }
}
