package com.android.personalmushaf.model.mushafs.strategies.quranstrategies;



public interface QuranStrategy {
    int pageNumberToDualPagerPosition(int pageNumber);

    int dualPagerPositionToPageNumber(int dualPagerPosition);

    int pageNumberToSinglePagerPosition(int pageNumber);

    int singlePagerPositionToPageNumber(int position);

    int minPage();

    int maxPage();

    int getPageNumberFromPagerPosition(int position);

    int getNumOfSinglePages();

    int getNumOfDualPages();
}
