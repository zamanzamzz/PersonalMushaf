package com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies;

import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;

public class ConcreteQuranPageAdapterStrategy implements QuranPageAdapterStrategy {
    private MushafMetadata mushafMetadata;
    private int numOfSinglePages;
    private int numOfDualPages;

    public ConcreteQuranPageAdapterStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
        numOfSinglePages = mushafMetadata.getMaxPage() - mushafMetadata.getMinPage() + 1;
        numOfDualPages = numOfSinglePages % 2 == 0 ? numOfSinglePages / 2 : numOfSinglePages / 2 + 1;
    }

    public int getPageNumberFromPagerPosition(int position) {
        return position + mushafMetadata.getMinPage();
    }

    public int getNumOfSinglePages() {
        return numOfSinglePages;
    }

    public int getNumOfDualPages() {
        return numOfDualPages;
    }
}
