package com.android.naskh15.quranstrategies;

import com.android.naskh15.Naskh15NavigationData;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies.AbstractQuranPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;

public class Naskh15QuranDualPageFragmentStrategy extends AbstractQuranPageFragmentStrategy implements QuranDualPageFragmentStrategy {

    public Naskh15QuranDualPageFragmentStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return getPagePath(Naskh15NavigationData.naskh15LineDualPageSets[dualPagerPosition][LEFTPAGEINDEX]);
    }

    public String getRightPagePath(int dualPagerPosition) {
        return getPagePath(Naskh15NavigationData.naskh15LineDualPageSets[dualPagerPosition][RIGHTPAGEINDEX]);
    }

    public PageData getLeftPageData(int dualPagerPosition) {
        return getPageData(Naskh15NavigationData.naskh15LineDualPageSets[dualPagerPosition][LEFTPAGEINDEX]);
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return getPageData(Naskh15NavigationData.naskh15LineDualPageSets[dualPagerPosition][RIGHTPAGEINDEX]);
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        return false;
    }
}
