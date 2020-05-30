package com.ammanz.personalmushaf;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadataFactory;
import com.ammanz.personalmushaf.util.FileUtils;
import com.google.android.play.core.assetpacks.AssetPackManager;
import com.google.android.play.core.assetpacks.AssetPackManagerFactory;
import com.google.android.play.core.assetpacks.AssetPackState;
import com.google.android.play.core.assetpacks.AssetPackStates;
import com.google.android.play.core.assetpacks.model.AssetPackStatus;
import com.google.android.play.core.tasks.Task;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QuranSettings {
    public static final int MODERN_NASKH_13_LINE = 0;
    public static final int CLASSIC_NASKH_15_LINE = 1;
    public static final int CLASSIC_MADANI_15_LINE = 2;
    public static final int DEFAULT_LANDMARK_SYSTEM = 0;
    public static final int RUKU = 1;
    public static final int HIZB = 2;
    private static boolean[] availableMushafs = {false, false, false};
    private AssetPackManager assetPackManager;
    private static QuranSettings quranSettings;
    private MushafMetadata mushafMetadata;
    private SharedPreferences preferences;
    private Integer mushafVersion;
    private Integer landmarkSystem;
    private Boolean isForceDualPage;
    private Boolean isSmoothKeyNavigation;
    private Boolean isDebugMode;

    public static QuranSettings getInstance() {
        if (quranSettings == null)
            quranSettings = new QuranSettings();
        return quranSettings;
    }



    private void setPreference(Context context) {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getMushafVersion(Context context) {
        if (mushafVersion == null) {
            setPreference(context);
            mushafVersion = Integer.parseInt(preferences.getString("mushaf", Integer.toString(CLASSIC_MADANI_15_LINE)));
        }

        return mushafVersion;
    }

    public void setMushafVersion(Integer mushafVersion) {
        this.mushafVersion = mushafVersion;
    }

    public int getLandMarkSystem(Context context) {
        if (landmarkSystem == null) {
            setPreference(context);
            landmarkSystem = Integer.parseInt(preferences.getString("landmark", Integer.toString(DEFAULT_LANDMARK_SYSTEM)));
        }

        return landmarkSystem;
    }

    public void setLandmarkSystem(Integer landmarkSystem) {
        this.landmarkSystem = landmarkSystem;
    }


    public MushafMetadata getMushafMetadata(Context context) {
        if (mushafMetadata == null)
            mushafMetadata = MushafMetadataFactory.getMushafMetadata(getMushafVersion(context));
        return mushafMetadata;
    }

    public void setMushafMetadata(int mushaf) {
        mushafMetadata = MushafMetadataFactory.getMushafMetadata(mushaf);
    }

    public void setMushafMetadata(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }



    public Boolean getIsForceDualPage(Context context) {
        if (isForceDualPage == null) {
            setPreference(context);
            isForceDualPage = preferences.getBoolean("force_dual_page", false);
        }

        return isForceDualPage;
    }

    private void initialAvailableMushafs(Context context) {
        assetPackManager = AssetPackManagerFactory.getInstance(context);
        for (int i = 0; i < availableMushafs.length; i++) {
            availableMushafs[i] = false;
        }
    }
    private void setAvailableMushaf(int mushaf, boolean status) {
        availableMushafs[mushaf] = status;
    }

    public boolean isMushafAvailable(int i) {
        return availableMushafs[i];
    }

    public void updateAvailableMushafs(Context context) {
        getInstance().initialAvailableMushafs(context);
        String[] packNamesArray = {"modernnaskh13line", "classicnaskh15line", "classicmadani15line"};
        List<String> packNames = Arrays.asList(packNamesArray);
        Task<AssetPackStates> assetPackStatesTask = assetPackManager.getPackStates(packNames);
        assetPackStatesTask.addOnSuccessListener(result -> {
            Map<String, AssetPackState> states = result.packStates();
            for (int i = MODERN_NASKH_13_LINE; i <= CLASSIC_MADANI_15_LINE; i++) {
                if (states.get(packNamesArray[i]).status() == AssetPackStatus.COMPLETED)
                    availableMushafs[i] = true;
            }
        }).addOnFailureListener(result -> {

        });
    }

    public boolean isAnyMushafAvailable() {
        for (boolean status: availableMushafs)
            if (status)
                return true;
        return false;
    }

    public boolean updateAvailableMushafsFirebase(Context context) {
        if (!FileUtils.checkRootDataDirectory())
            return false;

        String[] expectedMushafDirectories = {"modern_naskh_13_line", "classic_naskh_15_line", "classic_madani_15_line"};
        File currentMushafDirectory;
        File currentImagesDirectory;

        getInstance().initialAvailableMushafs(context);

        boolean rv = false;
        for (int i = MODERN_NASKH_13_LINE; i <= CLASSIC_NASKH_15_LINE + 1; i++) {
            currentMushafDirectory = new File(FileUtils.ASSETSDIRECTORY + "/" + expectedMushafDirectories[i]);
            if (currentMushafDirectory.exists() && currentMushafDirectory.isDirectory()) {
                currentImagesDirectory = new File(FileUtils.ASSETSDIRECTORY + "/" + expectedMushafDirectories[i] + "/images");
                if ((currentImagesDirectory.exists() && currentImagesDirectory.isDirectory()) && currentImagesDirectory.list().length > 500)
                    getInstance().setAvailableMushaf(i, true);
                rv = true;
            }
        }

        return rv;
    }


    public void setForceDualPage(Boolean forceDualPage) {
        isForceDualPage = forceDualPage;
    }

    public Boolean getIsSmoothKeyNavigation(Context context) {
        if (isSmoothKeyNavigation == null) {
            setPreference(context);
            isSmoothKeyNavigation = preferences.getBoolean("smoothpageturn", false);
        }

        return isSmoothKeyNavigation;
    }

    public void setSmoothKeyNavigation(Boolean smoothKeyNavigation) {
        isSmoothKeyNavigation = smoothKeyNavigation;
    }

    public void setDebugMode(Boolean isDebugMode) {
        this.isDebugMode = isDebugMode;
    }

    public Boolean getIsDebugMode(Context context) {
        if (isDebugMode == null) {
            setPreference(context);
            isDebugMode = preferences.getBoolean("debugmodeswitch", false);
        }

        return isDebugMode;
    }
}
