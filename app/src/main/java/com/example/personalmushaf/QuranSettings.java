package com.example.personalmushaf;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class QuranSettings {
    private static QuranSettings quranSettings;
    private SharedPreferences preferences;
    private String mushafVersion;
    private Boolean isForceDualPage;

    private Boolean isSmoothKeyNavigation;

    public static QuranSettings getInstance() {
        if (quranSettings == null)
            quranSettings = new QuranSettings();
        return quranSettings;
    }

    public String getMushafVersion(Context context) {
        if (mushafVersion == null) {
            setPreference(context);
            mushafVersion = preferences.getString("mushaf", "madani_15_line");
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

    public void setMushafVersion(String mushafVersion) {
        this.mushafVersion = mushafVersion;
    }

    public void setForceDualPage(Boolean forceDualPage) {
        isForceDualPage = forceDualPage;
    }

    public void setSmoothKeyNavigation(Boolean smoothKeyNavigation) {
        isSmoothKeyNavigation = smoothKeyNavigation;
    }
}
