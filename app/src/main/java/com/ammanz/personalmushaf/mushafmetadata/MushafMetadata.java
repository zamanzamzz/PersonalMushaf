package com.ammanz.personalmushaf.mushafmetadata;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.navigation.NavigationData;

public abstract class MushafMetadata {
    protected QuranSettings quranSettings = QuranSettings.getInstance();
    protected String assetPath;
    protected String id;
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

    public String getDatabasePath() {
        return assetPath + "/databases/ayahinfo_" + id + ".db";
    }

    public String getPagePath(int pageNumber) {
        return assetPath + "/images/" + id + "_pg_" + pageNumber + ".png";
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
