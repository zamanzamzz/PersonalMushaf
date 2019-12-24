package com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies;

import com.android.personalmushaf.model.PageData;

public interface QuranPageFragmentStrategy {
    String getPagePath(int pageNumber);

    PageData getPageData(int pageNumber);
}
