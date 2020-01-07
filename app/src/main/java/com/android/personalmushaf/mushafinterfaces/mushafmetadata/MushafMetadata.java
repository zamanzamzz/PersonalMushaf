package com.android.personalmushaf.mushafinterfaces.mushafmetadata;

public abstract class MushafMetadata {
    protected String directoryName;
    protected String databasePath;
    protected String name;
    protected String description;
    protected int[] previewDrawableIDs;

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
