package com.android.madani15;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.FileUtils;

public class ModernMadani15MushafMetadata extends MushafMetadata {

    public ModernMadani15MushafMetadata() {
        directoryName = "modern_madani_15_line";
        databasePath = FileUtils.ASSETSDIRECTORY + "/" + directoryName + "/databases/ayahinfo_modernmadani15line.db";
        name = "Modern 15 Line Madani Mushaf";
        description = "The modern standard mushaf from Saudi Arabia.";
        previewDrawableIDs = new int[2];
        /*previewDrawableIDs[0] = R.drawable.classic_madani_15_line_preview1;
        previewDrawableIDs[1] = R.drawable.classic_madani_15_line_preview2;*/
    }
}
