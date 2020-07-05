package com.ammanz.naskh13;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.navigation.NavigationData;

public class ModernNaskh13UncroppedMushafMetadata extends MushafMetadata {

    public ModernNaskh13UncroppedMushafMetadata() {
        assetName = "modernnaskh13uncroppedassets";
        assetPath = QuranSettings.getInstance().getMushafLocation(QuranSettings.MODERNNASKH13UNCROPPED);
        id = "modernnaskh13uncropped";
        databasePath = assetPath + "/databases/ayahinfo_modernnaskh13uncropped.db";
        name = "Modern 13 Line Naskh Mushaf (Uncropped)";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.modernnaskh13uncropped_preview1;
        previewDrawableIDs[1] = R.drawable.modernnaskh13uncropped_preview2;

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
        return landmarkSystem == QuranSettings.DEFAULTLANDMARKSYSTEM || landmarkSystem == QuranSettings.RUKU;
    }
}
