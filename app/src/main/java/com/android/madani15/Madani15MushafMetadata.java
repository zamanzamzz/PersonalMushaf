package com.android.madani15;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.navigation.QuranConstants;

public class Madani15MushafMetadata implements MushafMetadata {
    private String directoryName;
    private String databasePath;
    private String name;
    private String description;
    private int[] previewDrawableIDs;

    public Madani15MushafMetadata() {
        directoryName = "madani_15_line";
        databasePath = QuranConstants.ASSETSDIRECTORY + "/" + directoryName + "/databases/ayahinfo_15line.db";
        name = "Classic 15 Line Madani Mushaf";
        description = "The classic standard mushaf from Saudi Arabia.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.madani_15_line_preview1;
        previewDrawableIDs[1] = R.drawable.madani_15_line_preview2;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int[] getPreviewDrawableIDs() {
        return previewDrawableIDs;
    }
}
