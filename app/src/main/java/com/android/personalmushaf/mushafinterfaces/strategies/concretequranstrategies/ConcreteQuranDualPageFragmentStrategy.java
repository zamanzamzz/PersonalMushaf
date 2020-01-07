package com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies.AbstractQuranPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;

public class ConcreteQuranDualPageFragmentStrategy extends AbstractQuranPageFragmentStrategy implements QuranDualPageFragmentStrategy {

    public ConcreteQuranDualPageFragmentStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
    }

    public String getLeftPagePath(int dualPagerPosition) {
        return getPagePath(dualPagerPositionToPageNumber(dualPagerPosition));
    }

    public String getRightPagePath(int dualPagerPosition) {
        return getPagePath(dualPagerPositionToPageNumber(dualPagerPosition) + 1);
    }

    public PageData getLeftPageData(int dualPagerPosition) {
        return getPageData(dualPagerPositionToPageNumber(dualPagerPosition));
    }

    public PageData getRightPageData(int dualPagerPosition) {
        return getPageData(dualPagerPositionToPageNumber(dualPagerPosition) + 1);
    }

    public boolean isDanglingPage(int dualPagerPosition) {
        if (mushafMetadata.getDanglingDualPage() < 0)
            return false;
        else
            return dualPagerPosition == mushafMetadata.getDanglingDualPage();
    }
}
