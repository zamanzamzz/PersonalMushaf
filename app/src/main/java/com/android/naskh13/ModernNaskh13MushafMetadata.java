package com.android.naskh13;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.navigation.NavigationData;
import com.android.personalmushaf.util.FileUtils;

public class ModernNaskh13MushafMetadata extends MushafMetadata {

    public ModernNaskh13MushafMetadata() {
        directoryName = "modern_naskh_13_line";
        databasePath = FileUtils.ASSETSDIRECTORY + "/" + directoryName + "/databases/ayahinfo_modernnaskh13line.db";
        name = "Modern 13 Line Naskh Mushaf";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.modern_naskh_13_line_preview1;
        previewDrawableIDs[1] = R.drawable.modern_naskh_13_line_preview2;

        minPage = 2;
        maxPage = 848;
        danglingDualPage = 423;
        doesAyahSpanPages = true;
        downloadSize = 128.43;
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
