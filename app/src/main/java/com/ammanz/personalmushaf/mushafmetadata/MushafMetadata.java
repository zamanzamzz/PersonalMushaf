package com.ammanz.personalmushaf.mushafmetadata;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.navigation.NavigationData;

public abstract class MushafMetadata {
    protected QuranSettings quranSettings = QuranSettings.getInstance();
    protected String assetName;
    protected String assetPath;
    protected String id;
    protected String databasePath;
    protected String name;
    protected String description;
    protected int[] previewDrawableIDs;
    protected NavigationData navigationData;
    protected int danglingDualPage;

    protected boolean doesAyahSpanPages;
    protected int minPage;
    protected int maxPage;
    protected double downloadSize;

    public String getId() {
        return id;
    }
    public String getAssetName() {
        return assetName;
    }

    public String getAssetPath() {
        return assetPath;
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

    public double getDownloadSize() {
        return downloadSize;
    }

    public boolean doesAyahSpanPages() {
        return doesAyahSpanPages;
    }

    public abstract NavigationData getNavigationData();

    public abstract boolean getShouldDoRuku(int landmarkSystem);

}
