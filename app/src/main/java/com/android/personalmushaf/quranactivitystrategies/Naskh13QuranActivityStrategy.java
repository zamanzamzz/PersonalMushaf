package com.android.personalmushaf.quranactivitystrategies;

public class Naskh13QuranActivityStrategy implements QuranActivityStrategy {

    public Naskh13QuranActivityStrategy() {

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
}
