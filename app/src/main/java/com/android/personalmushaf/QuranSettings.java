package com.android.personalmushaf;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class QuranSettings {
    public static final int MADANI15LINE = 0;
    public static final int NASKH13LINE = 1;
    private static QuranSettings quranSettings;
    private SharedPreferences preferences;
    private Integer mushafVersion;
    private Boolean isForceDualPage;

    private Boolean isSmoothKeyNavigation;

    public static QuranSettings getInstance() {
        if (quranSettings == null)
            quranSettings = new QuranSettings();
        return quranSettings;
    }

    public int getMushafVersion(Context context) {
        if (mushafVersion == null) {
            setPreference(context);
            mushafVersion = Integer.parseInt(preferences.getString("mushaf", Integer.toString(MADANI15LINE)));
        }

        return mushafVersion;
    }

    public Boolean getIsForceDualPage(Context context) {
        if (isForceDualPage == null) {
            setPreference(context);
            isForceDualPage = preferences.getBoolean("force_dual_page", false);
        }

        return isForceDualPage;
    }

    public Boolean getIsSmoothKeyNavigation(Context context) {
        if (isSmoothKeyNavigation == null) {
            setPreference(context);
            isSmoothKeyNavigation = preferences.getBoolean("smoothpageturn", false);
        }

        return isSmoothKeyNavigation;
    }

    private void setPreference(Context context) {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setMushafVersion(Integer mushafVersion) {
        this.mushafVersion = mushafVersion;
    }

    public void setForceDualPage(Boolean forceDualPage) {
        isForceDualPage = forceDualPage;
    }

    public void setSmoothKeyNavigation(Boolean smoothKeyNavigation) {
        isSmoothKeyNavigation = smoothKeyNavigation;
    }
}
