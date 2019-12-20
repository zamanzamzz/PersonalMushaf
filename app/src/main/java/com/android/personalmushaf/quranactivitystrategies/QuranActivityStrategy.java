package com.android.personalmushaf.quranactivitystrategies;



public interface QuranActivityStrategy {
    int pageNumberToDualPagerPosition(int pageNumber);

    int dualPagerPositionToPageNumber(int dualPagerPosition);

    int pageNumberToSinglePagerPosition(int pageNumber);

    int singlePagerPositionToPageNumber(int position);

    int minPage();

    int maxPage();
}
