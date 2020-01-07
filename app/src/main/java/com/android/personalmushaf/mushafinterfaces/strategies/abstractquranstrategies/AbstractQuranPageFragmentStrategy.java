package com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.FileUtils;

public abstract class AbstractQuranPageFragmentStrategy extends AbstractSinglePageToDualPage {

    protected AbstractQuranPageFragmentStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
    }

    protected String getPagePath(int pageNumber) {
        return FileUtils.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + pageNumber + ".png";
    }

    protected PageData getPageData(int pageNumber) {
        return new PageData(pageNumber, mushafMetadata.getDatabasePath());
    }
}
