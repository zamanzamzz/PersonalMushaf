package com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies;

import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranActivityStrategy;

public class ConcreteQuranActivityStrategy implements QuranActivityStrategy {
    private MushafMetadata mushafMetadata;

    public ConcreteQuranActivityStrategy(MushafMetadata mushafMetadata) {
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

    public int pageNumberToSinglePagerPosition(int pageNumber) {
        return pageNumber - getMinPage();
    }

    public int singlePagerPositionToPageNumber(int position) {
        return position + getMinPage();
    }

    public int getMinPage() {
        return mushafMetadata.getMinPage();
    }

    public int getMaxPage() {
        return mushafMetadata.getMaxPage();
    }
}
