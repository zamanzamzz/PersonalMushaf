package com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies;

import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;

public class ConcreteQuranPageAdapterStrategy implements QuranPageAdapterStrategy {
    private MushafMetadata mushafMetadata;

    public ConcreteQuranPageAdapterStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public int getPageNumberFromPagerPosition(int position) {
        return position + mushafMetadata.getMinPage();
    }

    public int getNumOfSinglePages() {
        return mushafMetadata.getMaxPage() - mushafMetadata.getMinPage() + 1;
    }

    public int getNumOfDualPages() {
        return mushafMetadata.getNavigationData().getDualPageSets().length;
    }
}
