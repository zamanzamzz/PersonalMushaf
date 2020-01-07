package com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies;

import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractquranstrategies.AbstractSinglePageToDualPage;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranActivityStrategy;

public class ConcreteQuranActivityStrategy extends AbstractSinglePageToDualPage implements QuranActivityStrategy {

    public ConcreteQuranActivityStrategy(MushafMetadata mushafMetadata) {
        super(mushafMetadata);
    }



    public int pageNumberToSinglePagerPosition(int pageNumber) {
        return pageNumber - getMinPage();
    }

    public int singlePagerPositionToPageNumber(int position) {
        return position + getMinPage();
    }
}
