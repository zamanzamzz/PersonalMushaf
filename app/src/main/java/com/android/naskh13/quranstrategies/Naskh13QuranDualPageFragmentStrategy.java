package com.android.naskh13.quranstrategies;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;
import com.android.personalmushaf.navigation.QuranConstants;

public class Naskh13QuranDualPageFragmentStrategy implements QuranDualPageFragmentStrategy {
    private MushafMetadata mushafMetadata;

    public Naskh13QuranDualPageFragmentStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return getPagePath(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][0]);
    }

    public String getRightPagePath(int dualPagerPosition) {
        return getPagePath(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][1]);
    }

    public PageData getLeftPageData(int dualPagerPosition) {
        return getPageData(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][0]);
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return getPageData(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][1]);
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        return dualPagerPosition == 423;
    }

    private String getPagePath(int pageNumber) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + pageNumber + ".png";
    }

    private PageData getPageData(int pageNumber) {
        return new PageData(pageNumber, mushafMetadata.getDatabasePath());
    }
}
