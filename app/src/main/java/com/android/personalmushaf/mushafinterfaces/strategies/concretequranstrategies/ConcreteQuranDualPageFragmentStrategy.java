package com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies.AbstractQuranPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;

public class ConcreteQuranDualPageFragmentStrategy extends AbstractQuranPageFragmentStrategy implements QuranDualPageFragmentStrategy {
    private int[][] dualPageSets;

    public ConcreteQuranDualPageFragmentStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
        dualPageSets = mushafMetadata.getNavigationData().getDualPageSets();
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return getPagePath(dualPageSets[dualPagerPosition][LEFTPAGEINDEX]);
    }

    public String getRightPagePath(int dualPagerPosition) {
        return getPagePath(dualPageSets[dualPagerPosition][RIGHTPAGEINDEX]);
    }

    public PageData getLeftPageData(int dualPagerPosition) {
        return getPageData(dualPageSets[dualPagerPosition][LEFTPAGEINDEX]);
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return getPageData(dualPageSets[dualPagerPosition][RIGHTPAGEINDEX]);
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        if (mushafMetadata.getDanglingDualPage() < 0)
            return false;
        else
            return dualPagerPosition == mushafMetadata.getDanglingDualPage();
    }
}
