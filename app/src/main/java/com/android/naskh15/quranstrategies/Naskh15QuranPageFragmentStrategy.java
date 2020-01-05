package com.android.naskh15.quranstrategies;

import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies.AbstractQuranPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageFragmentStrategy;


public class Naskh15QuranPageFragmentStrategy extends AbstractQuranPageFragmentStrategy implements QuranPageFragmentStrategy {

    public Naskh15QuranPageFragmentStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
    }

    public String getPagePath(int pageNumber) {
        return super.getPagePath(pageNumber);
    }

    public PageData getPageData(int pageNumber) {
        return super.getPageData(pageNumber);
    }
}
