package com.ammanz.naskh15;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.navigation.NavigationData;

public class ClassicNaskh15MushafMetadata extends MushafMetadata {

    public ClassicNaskh15MushafMetadata() {
        assetName = "classicnaskh15assets";
        assetPath = QuranSettings.getInstance().getMushafLocation(QuranSettings.CLASSICNASKH15);
        id = "classicnaskh15";
        databasePath = assetPath + "/databases/ayahinfo_classicnaskh15line.db";
        name = "Classic 15 Line Naskh Mushaf";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.classic_naskh_15_line_preview1;
        previewDrawableIDs[1] = R.drawable.classic_naskh_15_line_preview2;

        minPage = 2;
        maxPage = 611;
        danglingDualPage = -1;
        doesAyahSpanPages = false;
        downloadSize = 275.63;
    }


    public NavigationData getNavigationData() {
        if (navigationData == null)
            navigationData = new Naskh15NavigationData();

        return navigationData;
    }

    public boolean getShouldDoRuku(int landmarkSystem) {
        return landmarkSystem == QuranSettings.DEFAULTLANDMARKSYSTEM || landmarkSystem == QuranSettings.RUKU;
    }
}
