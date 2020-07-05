package com.ammanz.naskh13;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.navigation.NavigationData;

public class ModernNaskh13MushafMetadata extends MushafMetadata {

    public ModernNaskh13MushafMetadata() {
        assetName = "modernnaskh13assets";
        assetPath = QuranSettings.getInstance().getMushafLocation(QuranSettings.MODERN_NASKH_13_LINE);
        id = "modernnaskh13";
        databasePath = assetPath + "/databases/ayahinfo_modernnaskh13line.db";
        name = "Modern 13 Line Naskh Mushaf";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.modern_naskh_13_line_preview1;
        previewDrawableIDs[1] = R.drawable.modern_naskh_13_line_preview2;

        minPage = 2;
        maxPage = 848;
        danglingDualPage = 423;
        doesAyahSpanPages = true;
        downloadSize = 85.7;
    }


    public NavigationData getNavigationData() {
        if (navigationData == null) {
            navigationData = new Naskh13NavigationData();
        }

        return navigationData;
    }

    public boolean getShouldDoRuku(int landmarkSystem) {
        return landmarkSystem == QuranSettings.DEFAULT_LANDMARK_SYSTEM || landmarkSystem == QuranSettings.RUKU;
    }
}
