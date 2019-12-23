package com.android.personalmushaf.model.mushafs.strategies.quranstrategies;

import com.android.personalmushaf.navigation.navigationdata.QuranConstants;

public class Madani15QuranStrategy implements QuranStrategy {

    public Madani15QuranStrategy() {

    }

    public int pageNumberToDualPagerPosition(int pageNumber) {
        if (pageNumber % 2 == 0)
            return pageNumber / 2 - 1;
        else
            return (pageNumber - 1) / 2;
    }

    public int dualPagerPositionToPageNumber(int dualPagerPosition) {
        return dualPagerPosition *2 + 1;
    }

    public int pageNumberToSinglePagerPosition(int pageNumber) {
        return pageNumber - 1;
    }

    public int singlePagerPositionToPageNumber(int position) {
        return position + 1;
    }

    public int minPage() {
        return 0;
    }

    public int maxPage() {
        return 604;
    }

    public int getPageNumberFromPagerPosition(int position) {
        return position;
    }

    public int getNumOfSinglePages() {
        return 604;
    }

    public int getNumOfDualPages() {
        return QuranConstants.madani15LineDualPageSets.length;
    }
}
