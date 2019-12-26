package com.android.personalmushaf;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.android.personalmushaf.mushafinterfaces.strategies.MushafStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.MushafStrategyFactory;
import com.android.personalmushaf.util.FileUtils;

import java.io.File;

public class QuranSettings {
    public static final int NASKH13LINE = 0;
    public static final int NASKH15LINE = 1;
    public static final int MADANI15LINE = 2;
    private static boolean[] availableMushafs = {false, false, false};
    private static QuranSettings quranSettings;
    private MushafStrategy mushafStrategy;
    private SharedPreferences preferences;
    private Integer mushafVersion;
    private Boolean isForceDualPage;

    private Boolean isSmoothKeyNavigation;

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
            mushafVersion = Integer.parseInt(preferences.getString("mushaf", Integer.toString(MADANI15LINE)));
        }

        return mushafVersion;
    }

    public void setMushafVersion(Integer mushafVersion) {
        this.mushafVersion = mushafVersion;
    }

    public MushafStrategy getMushafStrategy(Context context) {
        if (mushafStrategy == null)
            mushafStrategy = MushafStrategyFactory.getMushafStrategy(getMushafVersion(context));
        return mushafStrategy;
    }

    public void setMushafStrategy(int mushaf) {
        mushafStrategy = MushafStrategyFactory.getMushafStrategy(mushaf);
    }

    public Boolean getIsForceDualPage(Context context) {
        if (isForceDualPage == null) {
            setPreference(context);
            isForceDualPage = preferences.getBoolean("force_dual_page", false);
        }

        return isForceDualPage;
    }

    private void initialAvailableMushafs() {
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

    public boolean updateAvailableMushafs() {
        if (!FileUtils.checkRootDataDirectory())
            return false;

        String[] expectedMushafDirectories = {"naskh_13_line", "naskh_15_line", "madani_15_line"};
        File currentMushafDirectory;
        File currentImagesDirectory;

        getInstance().initialAvailableMushafs();

        boolean rv = false;
        for (int i = NASKH13LINE; i <= NASKH15LINE + 1; i++) {
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
}
