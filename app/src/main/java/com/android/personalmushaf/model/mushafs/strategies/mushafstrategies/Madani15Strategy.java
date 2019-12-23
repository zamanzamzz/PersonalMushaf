package com.android.personalmushaf.model.mushafs.strategies.mushafstrategies;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.model.mushafs.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.model.mushafs.strategies.navigationstrategies.Madani15NavigationStrategy;
import com.android.personalmushaf.model.mushafs.strategies.navigationstrategies.NavigationStrategy;
import com.android.personalmushaf.model.mushafs.strategies.quranstrategies.Madani15QuranStrategy;
import com.android.personalmushaf.model.mushafs.strategies.quranstrategies.QuranStrategy;

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
