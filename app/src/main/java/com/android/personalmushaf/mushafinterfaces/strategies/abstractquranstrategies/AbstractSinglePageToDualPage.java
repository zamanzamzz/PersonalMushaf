package com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies;

import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;

public abstract class AbstractSinglePageToDualPage {
    protected MushafMetadata mushafMetadata;

    public AbstractSinglePageToDualPage(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public int pageNumberToDualPagerPosition(int pageNumber) {
        if (pageNumber % 2 == 0)
            return pageNumber / 2 - 1;
        else
            return (pageNumber - 1) / 2 - (getMinPage() - 1);
    }

    public int dualPagerPositionToPageNumber(int dualPagerPosition) {
        return dualPagerPosition * 2 + getMinPage();
    }

    public int getMinPage() {
        return mushafMetadata.getMinPage();
    }

    public int getMaxPage() {
        return mushafMetadata.getMaxPage();
    }
}
