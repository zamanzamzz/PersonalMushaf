package com.android.naskh15.quranstrategies;

import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranActivityStrategy;

public class Naskh15QuranActivityStrategy implements QuranActivityStrategy {
    public int pageNumberToDualPagerPosition(int pageNumber) {
        if (pageNumber % 2 == 0)
            return pageNumber / 2 - 1;
        else
            return (pageNumber - 1) / 2 - 1;
    }

    public int dualPagerPositionToPageNumber(int dualPagerPosition) {
        return dualPagerPosition * 2 + 2;
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
        return 611;
    }
}
