package com.android.naskh15.quranstrategies;

import com.android.naskh13.Naskh13NavigationData;
import com.android.naskh15.Naskh15NavigationData;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;

public class Naskh15QuranPageAdapterStrategy implements QuranPageAdapterStrategy {
    public int getPageNumberFromPagerPosition(int position) {
        return position + 2;
    }

    public int getNumOfSinglePages() {
        return 610;
    }

    public int getNumOfDualPages() {
        return Naskh15NavigationData.naskh15LineDualPageSets.length;
    }
}
