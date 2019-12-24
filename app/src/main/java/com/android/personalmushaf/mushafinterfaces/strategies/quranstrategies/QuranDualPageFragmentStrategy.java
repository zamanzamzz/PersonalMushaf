package com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies;

import com.android.personalmushaf.model.PageData;

public interface QuranDualPageFragmentStrategy {
    String getRightPagePath(int dualPagerPosition);

    String getLeftPagePath(int dualPagerPosition);

    PageData getRightPageData(int dualPagerPosition);

    PageData getLeftPageData(int dualPagerPosition);

    boolean isDanglingPage(int dualPagerPosition);
}
