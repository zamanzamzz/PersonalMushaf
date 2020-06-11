package com.ammanz.madani15;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.navigation.NavigationData;

public class ClassicMadani15MushafMetadata extends MushafMetadata {

    public ClassicMadani15MushafMetadata() {
        assetName = "classicmadani15assets";
        assetPath = QuranSettings.getInstance().getMushafLocation(QuranSettings.CLASSIC_MADANI_15_LINE);
        id = "classicmadani15";
        databasePath = assetPath + "/databases/ayahinfo_classicmadani15line.db";
        name = "Classic 15 Line Madani Mushaf";
        description = "The classic standard mushaf from Saudi Arabia.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.classic_madani_15_line_preview1;
        previewDrawableIDs[1] = R.drawable.classic_madani_15_line_preview2;
        minPage = 1;
        maxPage = 604;
        danglingDualPage = -1;
        doesAyahSpanPages = false;
        downloadSize = 93.2;
    }

    public NavigationData getNavigationData() {
        if (navigationData == null)
            navigationData = new Madani15NavigationData();

        return navigationData;
    }

    public boolean getShouldDoRuku(int landmarkSystem) {
        return landmarkSystem == QuranSettings.RUKU;
    }
}
