package com.ammanz.personalmushaf;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadataFactory;
import com.ammanz.personalmushaf.util.FileUtils;
import com.google.android.play.core.assetpacks.AssetPackLocation;
import com.google.android.play.core.assetpacks.AssetPackManager;
import com.google.android.play.core.assetpacks.AssetPackManagerFactory;

import java.io.File;

public class QuranSettings {
    public static final int MODERNNASKH13CROPPED = 0;
    public static final int MODERNNASKH13UNCROPPED = 1;
    public static final int CLASSICNASKH15 = 2;
    public static final int CLASSICMADANI15 = 3;
    public static final int DEFAULTLANDMARKSYSTEM = 0;
    public static final int RUKU = 1;
    public static final int HIZB = 2;
    private static boolean[] availableMushafs = {false, false, false, false};
    private String[] packNamesArray = {"modernnaskh13cropped", "modernnaskh13uncropped", "classicnaskh15", "classicmadani15"};
    private AssetPackManager assetPackManager;
    private static QuranSettings quranSettings;
    private MushafMetadata mushafMetadata;
    private SharedPreferences preferences;
    private Integer mushafVersion;
    private Integer landmarkSystem;
    private Boolean isForceDualPage;
    private Boolean isSmoothKeyNavigation;
    private Boolean isDebugMode;
    private Boolean nightMode;
    private Boolean simplifyInterface;
    private Boolean shouldRestartNavigationActivity = false;

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
            mushafVersion = Integer.parseInt(preferences.getString("mushaf", Integer.toString(CLASSICMADANI15)));
        }

        return mushafVersion;
    }

    public void setMushafVersion(Integer mushafVersion) {
        this.mushafVersion = mushafVersion;
    }

    public String getMushafLocation(int mushafVersion) {
        AssetPackLocation location = assetPackManager.getPackLocation(packNamesArray[mushafVersion]);
        return location == null ? null : location.assetsPath();
    }

    public void setNightMode(Boolean nightMode) {
        this.nightMode = nightMode;
    }

    public Boolean getNightMode(Context context) {
        if (nightMode == null) {
            setPreference(context);
            nightMode = preferences.getBoolean("night_mode", false);
        }

        return nightMode;
    }

    public void setSimplifyInterface(Boolean simplifyInterface) {
        this.simplifyInterface = simplifyInterface;
    }

    public Boolean getSimplifyInterface(Context context) {
        if (simplifyInterface == null) {
            setPreference(context);
            simplifyInterface = preferences.getBoolean("simplify_interface", false);
        }

        return simplifyInterface;
    }

    public int getLandMarkSystem(Context context) {
        if (landmarkSystem == null) {
            setPreference(context);
            landmarkSystem = Integer.parseInt(preferences.getString("landmark", Integer.toString(DEFAULTLANDMARKSYSTEM)));
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

    public void initializeAvailableMushafs(Context context) {
        assetPackManager = AssetPackManagerFactory.getInstance(context);
        for (int i = 0; i < availableMushafs.length; i++) {
            availableMushafs[i] = false;
        }
    }

    public void setAvailableMushaf(int mushaf, boolean status) {
        availableMushafs[mushaf] = status;
    }

    public boolean isMushafAvailable(int i) {
        return availableMushafs[i];
    }

    public void updateAvailableMushafs(Context context) {
        initializeAvailableMushafs(context);
        for (int i = MODERNNASKH13CROPPED; i <= CLASSICMADANI15; i++) {
            if (assetPackManager.getPackLocation(packNamesArray[i]) != null)
                setAvailableMushaf(i, true);
        }
    }

    public boolean isAnyMushafAvailable() {
        for (boolean status: availableMushafs)
            if (status)
                return true;
        return false;
    }

    public boolean updateAvailableMushafsDebug(Context context) {
        if (!FileUtils.checkRootDataDirectory())
            return false;

        String[] expectedMushafDirectories = {"modernnaskh13cropped", "modernnaskh13uncropped", "classicnaskh15", "classicmadani15"};
        File currentMushafDirectory;
        File currentImagesDirectory;

        initializeAvailableMushafs(context);

        boolean rv = false;
        for (int i = MODERNNASKH13CROPPED; i <= CLASSICNASKH15 + 1; i++) {
            currentMushafDirectory = new File(FileUtils.ASSETSDIRECTORY + "/" + expectedMushafDirectories[i]);
            if (currentMushafDirectory.exists() && currentMushafDirectory.isDirectory()) {
                currentImagesDirectory = new File(FileUtils.ASSETSDIRECTORY + "/" + expectedMushafDirectories[i] + "/images");
                if ((currentImagesDirectory.exists() && currentImagesDirectory.isDirectory()) && currentImagesDirectory.list().length > 500)
                    setAvailableMushaf(i, true);
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

    public Boolean getShouldRestartNavigationActivity() {
        return shouldRestartNavigationActivity;
    }

    public void setShouldRestartNavigationActivity(Boolean shouldRestartNavigationActivity) {
        this.shouldRestartNavigationActivity = shouldRestartNavigationActivity;
    }
}
