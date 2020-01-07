package com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.FileUtils;

public abstract class AbstractQuranPageFragmentStrategy {
    protected MushafMetadata mushafMetadata;

    protected static final int LEFTPAGEINDEX = 0;
    protected static final int RIGHTPAGEINDEX = 1;

    protected AbstractQuranPageFragmentStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    protected String getPagePath(int pageNumber) {
        return FileUtils.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + pageNumber + ".png";
    }

    protected PageData getPageData(int pageNumber) {
        return new PageData(pageNumber, mushafMetadata.getDatabasePath());
    }
}
