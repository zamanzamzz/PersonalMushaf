package com.android.madani15;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.navigation.NavigationData;
import com.android.personalmushaf.util.FileUtils;

public class ClassicMadani15MushafMetadata extends MushafMetadata {

    public ClassicMadani15MushafMetadata() {
        directoryName = "classic_madani_15_line";
        databasePath = FileUtils.ASSETSDIRECTORY + "/" + directoryName + "/databases/ayahinfo_classicmadani15line.db";
        name = "Classic 15 Line Madani Mushaf";
        description = "The classic standard mushaf from Saudi Arabia.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.classic_madani_15_line_preview1;
        previewDrawableIDs[1] = R.drawable.classic_madani_15_line_preview2;

        minPage = 1;
        maxPage = 604;
        danglingDualPage = -1;
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
