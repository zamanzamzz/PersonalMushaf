package com.android.madani15.quranstrategies;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;
import com.android.personalmushaf.navigation.QuranConstants;

public class Madani15QuranDualPageFragmentStrategy implements QuranDualPageFragmentStrategy {
    private MushafMetadata mushafMetadata;

    public Madani15QuranDualPageFragmentStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return getPagePath(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][0]);
    }

    public String getRightPagePath(int dualPagerPosition) {
        return getPagePath(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][1]);
    }


    public PageData getLeftPageData(int dualPagerPosition) {
        return getPageData(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][0]);
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return getPageData(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][1]);
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        return false;
    }

    private String getPagePath(int pageNumber) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + pageNumber + ".png";
    }

    private PageData getPageData(int pageNumber) {
        return new PageData(pageNumber, mushafMetadata.getDatabasePath());
    }
}
