package com.android.madani15.quranstrategies;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies.AbstractQuranPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;

public class Madani15QuranDualPageFragmentStrategy extends AbstractQuranPageFragmentStrategy implements QuranDualPageFragmentStrategy {

    public Madani15QuranDualPageFragmentStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return getPagePath(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][LEFTPAGEINDEX]);
    }

    public String getRightPagePath(int dualPagerPosition) {
        return getPagePath(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][RIGHTPAGEINDEX]);
    }


    public PageData getLeftPageData(int dualPagerPosition) {
        return getPageData(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][LEFTPAGEINDEX]);
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return getPageData(Madani15NavigationData.madani15LineDualPageSets[dualPagerPosition][RIGHTPAGEINDEX]);
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        return false;
    }
}
