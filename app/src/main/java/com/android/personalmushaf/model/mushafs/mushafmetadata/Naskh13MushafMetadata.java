package com.android.personalmushaf.model.mushafs.mushafmetadata;

import com.android.personalmushaf.R;

public class Naskh13MushafMetadata implements MushafMetadata {
    private String directoryName;
    private String name;
    private String description;
    private int[] previewDrawableIDs;

    public Naskh13MushafMetadata() {
        directoryName = "naskh_13_line";
        name = "Modern 13 Line Naskh Mushaf";
        description = "Popular with huffadh in the Indian Subcontinent and South Africa.";
        previewDrawableIDs = new int[2];
        previewDrawableIDs[0] = R.drawable.naskh_13_line_preview1;
        previewDrawableIDs[1] = R.drawable.naskh_13_line_preview2;
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
