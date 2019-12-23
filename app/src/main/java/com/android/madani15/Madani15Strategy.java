package com.android.madani15;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.mushafinterfaces.strategies.MushafStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.NavigationStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.QuranStrategy;

public class Madani15Strategy implements MushafStrategy {

    private QuranStrategy quranStrategy;
    private NavigationStrategy navigationStrategy;

    public Madani15Strategy() {
        quranStrategy = new Madani15QuranStrategy(MushafMetadataFactory.getMushafMetadata(QuranSettings.MADANI15LINE));
        navigationStrategy = new Madani15NavigationStrategy();
    }

    public QuranStrategy getQuranStrategy() {
        return quranStrategy;
    }

    public NavigationStrategy getNavivationStrategy() {
        return navigationStrategy;
    }
}
