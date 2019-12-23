package com.android.personalmushaf.model.mushafs.mushafmetadata;

import com.android.personalmushaf.R;

public class Madani15MushafMetadata implements MushafMetadata {
    private String directoryName;
    private String name;
    private String description;
    private int[] previewDrawableIDs;

    public Madani15MushafMetadata() {
        directoryName = "madani_15_line";
        name = "Classic 15 Line Madani Mushaf";
        description = "The classic standard mushaf from Saudi Arabia.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.madani_15_line_preview1;
        previewDrawableIDs[1] = R.drawable.madani_15_line_preview2;
    }

    public String getDirectoryName() {
        return directoryName;
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
