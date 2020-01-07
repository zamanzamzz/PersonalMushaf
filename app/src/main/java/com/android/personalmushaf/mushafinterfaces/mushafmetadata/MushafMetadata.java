package com.android.personalmushaf.mushafinterfaces.mushafmetadata;

import com.android.personalmushaf.mushafinterfaces.NavigationData;

public abstract class MushafMetadata {
    protected String directoryName;
    protected String databasePath;
    protected String name;
    protected String description;
    protected int[] previewDrawableIDs;
    protected NavigationData navigationData;
    protected int danglingDualPage;

    protected int minPage;
    protected int maxPage;

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

    public int getMinPage() {
        return minPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getDanglingDualPage() {
        return danglingDualPage;
    }

    public abstract NavigationData getNavigationData();

    public abstract boolean getShouldDoRuku(int landmarkSystem);

}
