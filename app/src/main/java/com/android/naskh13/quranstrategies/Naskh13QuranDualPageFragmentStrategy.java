package com.android.naskh13.quranstrategies;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies.AbstractQuranPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;

public class Naskh13QuranDualPageFragmentStrategy extends AbstractQuranPageFragmentStrategy implements QuranDualPageFragmentStrategy {

    public Naskh13QuranDualPageFragmentStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return getPagePath(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][LEFTPAGEINDEX]);
    }

    public String getRightPagePath(int dualPagerPosition) {
        return getPagePath(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][RIGHTPAGEINDEX]);
    }

    public PageData getLeftPageData(int dualPagerPosition) {
        return getPageData(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][LEFTPAGEINDEX]);
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return getPageData(Naskh13NavigationData.naskh13LineDualPageSets[dualPagerPosition][RIGHTPAGEINDEX]);
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        return dualPagerPosition == 423;
    }
}
