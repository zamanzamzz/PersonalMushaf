package com.android.personalmushaf.model.mushafs.mushafmetadata;

public interface MushafMetadata {
    String getDirectoryName();
    String getDatabasePath();
    String getName();
    String getDescription();
    int[] getPreviewDrawableIDs();
}
