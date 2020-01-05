package com.android.naskh13;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.FileUtils;

public class ClassicNaskh13MushafMetadata implements MushafMetadata {
    private String directoryName;
    private String databasePath;
    private String name;
    private String description;
    private int[] previewDrawableIDs;

    public ClassicNaskh13MushafMetadata() {
        directoryName = "classic_naskh_13_line";
        databasePath = FileUtils.ASSETSDIRECTORY + "/" + directoryName + "/databases/ayahinfo_classicnaskh13line.db";
        name = "Classic 13 Line Naskh Mushaf";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        /*previewDrawableIDs[0] = R.drawable.modern_naskh_13_line_preview1;
        previewDrawableIDs[1] = R.drawable.modern_naskh_13_line_preview2;*/
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
