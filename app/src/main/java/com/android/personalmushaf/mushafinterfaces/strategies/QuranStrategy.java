package com.android.personalmushaf.mushafinterfaces.strategies;


import com.android.personalmushaf.model.PageData;

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

    String getPagePath(int pageNumber);

    PageData getPageData(int pageNumber);

    String getRightPagePath(int dualPagerPosition);

    String getLeftPagePath(int dualPagerPosition);

    PageData getRightPageData(int dualPagerPosition);

    PageData getLeftPageData(int dualPagerPosition);

    boolean isDanglingPage(int dualPagerPosition);
}
