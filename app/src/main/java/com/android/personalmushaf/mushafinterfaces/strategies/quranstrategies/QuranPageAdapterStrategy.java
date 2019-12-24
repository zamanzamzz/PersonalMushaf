package com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies;

public interface QuranPageAdapterStrategy {
    int getPageNumberFromPagerPosition(int position);

    int getNumOfSinglePages();

    int getNumOfDualPages();
}
