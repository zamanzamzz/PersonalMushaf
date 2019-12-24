package com.android.naskh13.quranstrategies;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageFragmentStrategy;
import com.android.personalmushaf.navigation.QuranConstants;

public class Naskh13QuranPageFragmentStrategy implements QuranPageFragmentStrategy {
    private MushafMetadata mushafMetadata;

    public Naskh13QuranPageFragmentStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public String getPagePath(int pageNumber) {
        return QuranConstants.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + pageNumber + ".png";
    }

    public PageData getPageData(int pageNumber) {
        return new PageData(pageNumber, mushafMetadata.getDatabasePath());
    }
}
