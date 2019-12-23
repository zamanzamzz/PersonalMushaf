package com.android.naskh13;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.mushafinterfaces.strategies.MushafStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.NavigationStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.QuranStrategy;

public class Naskh13Strategy implements MushafStrategy {

    private QuranStrategy quranStrategy;
    private NavigationStrategy navigationStrategy;

    public Naskh13Strategy() {
        quranStrategy = new Naskh13QuranStrategy(MushafMetadataFactory.getMushafMetadata(QuranSettings.NASKH13LINE));
        navigationStrategy = new Naskh13NavigationStrategy();
    }

    public QuranStrategy getQuranStrategy() {
        return quranStrategy;
    }

    public NavigationStrategy getNavivationStrategy() {
        return navigationStrategy;
    }
}
