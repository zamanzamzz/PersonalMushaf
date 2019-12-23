package com.android.personalmushaf.model.mushafs.strategies.quranstrategies;

import com.android.personalmushaf.navigation.navigationdata.QuranConstants;

public class Naskh13QuranStrategy implements QuranStrategy {

    public Naskh13QuranStrategy() {

    }

    public int pageNumberToDualPagerPosition(int pageNumber) {
        if (pageNumber % 2 == 0)
            return pageNumber / 2 - 1;
        else
            return (pageNumber - 1) / 2 - 1;
    }

    public int dualPagerPositionToPageNumber(int dualPagerPosition) {
        return dualPagerPosition *2 + 2;
    }

    public int pageNumberToSinglePagerPosition(int pageNumber) {
        return pageNumber - 2;
    }

    public int singlePagerPositionToPageNumber(int position) {
        return position + 2;
    }

    public int minPage() {
        return 2;
    }

    public int maxPage() {
        return 848;
    }

    public int getPageNumberFromPagerPosition(int position) {
        return position + 1;
    }

    public int getNumOfSinglePages() {
        return 847;
    }

    public int getNumOfDualPages() {
        return QuranConstants.naskh13LineDualPageSets.length;
    }
}
