package com.android.naskh13.quranstrategies;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;

public class Naskh13QuranPageAdapterStrategy implements QuranPageAdapterStrategy {
    public int getPageNumberFromPagerPosition(int position) {
        return position + 2;
    }

    public int getNumOfSinglePages() {
        return 847;
    }

    public int getNumOfDualPages() {
        return Naskh13NavigationData.naskh13LineDualPageSets.length;
    }
}
