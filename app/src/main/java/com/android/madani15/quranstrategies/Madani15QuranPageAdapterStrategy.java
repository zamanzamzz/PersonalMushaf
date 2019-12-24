package com.android.madani15.quranstrategies;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;

public class Madani15QuranPageAdapterStrategy implements QuranPageAdapterStrategy {
    public int getPageNumberFromPagerPosition(int position) {
        return position;
    }

    public int getNumOfSinglePages() {
        return 604;
    }

    public int getNumOfDualPages() {
        return Madani15NavigationData.madani15LineDualPageSets.length;
    }
}
