package com.android.madani15;

import com.android.madani15.navigationstrategies.Madani15JuzQuarterStrategy;
import com.android.madani15.navigationstrategies.Madani15JuzStrategy;
import com.android.madani15.navigationstrategies.Madani15NavigationActivityStrategy;
import com.android.madani15.navigationstrategies.Madani15RukuContentStrategy;
import com.android.madani15.navigationstrategies.Madani15SurahStrategy;
import com.android.madani15.quranstrategies.Madani15QuranActivityStrategy;
import com.android.madani15.quranstrategies.Madani15QuranDualPageFragmentStrategy;
import com.android.madani15.quranstrategies.Madani15QuranPageAdapterStrategy;
import com.android.madani15.quranstrategies.Madani15QuranPageFragmentStrategy;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.mushafinterfaces.strategies.MushafStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;

public class Madani15Strategy implements MushafStrategy {
    private MushafMetadata mushafMetadata;
    private Madani15NavigationActivityStrategy navigationActivityStrategy;
    private Madani15JuzQuarterStrategy juzQuarterStrategy;
    private Madani15JuzStrategy juzStrategy;
    private Madani15RukuContentStrategy rukuContentStrategy;
    private Madani15SurahStrategy surahStrategy;

    private Madani15QuranActivityStrategy quranActivityStrategy;
    private Madani15QuranPageAdapterStrategy quranPageAdapterStrategy;
    private Madani15QuranPageFragmentStrategy quranPageFragmentStrategy;
    private Madani15QuranDualPageFragmentStrategy quranDualPageFragmentStrategy;

    public Madani15Strategy() {
        mushafMetadata = MushafMetadataFactory.getMushafMetadata(QuranSettings.CLASSIC_MADANI_15_LINE);
        navigationActivityStrategy = new Madani15NavigationActivityStrategy();
        juzQuarterStrategy = new Madani15JuzQuarterStrategy();
        juzStrategy = new Madani15JuzStrategy();
        rukuContentStrategy = new Madani15RukuContentStrategy();
        surahStrategy = new Madani15SurahStrategy();

        quranActivityStrategy = new Madani15QuranActivityStrategy();
        quranPageAdapterStrategy = new Madani15QuranPageAdapterStrategy();
        quranPageFragmentStrategy = new Madani15QuranPageFragmentStrategy(mushafMetadata);
        quranDualPageFragmentStrategy = new Madani15QuranDualPageFragmentStrategy(mushafMetadata);
    }

    public JuzQuarterStrategy getJuzQuarterStrategy() {
        return juzQuarterStrategy;
    }

    public JuzStrategy getJuzStrategy() {
        return juzStrategy;
    }

    public NavigationActivityStrategy getNavigationActivityStrategy() {
        return navigationActivityStrategy;
    }

    public RukuContentStrategy getRukuContentStrategy() {
        return rukuContentStrategy;
    }

    public SurahStrategy getSurahStrategy() {
        return surahStrategy;
    }

    public Madani15QuranActivityStrategy getQuranActivityStrategy() {
        return quranActivityStrategy;
    }

    public Madani15QuranPageAdapterStrategy getQuranPageAdapterStrategy() {
        return quranPageAdapterStrategy;
    }

    public Madani15QuranPageFragmentStrategy getQuranPageFragmentStrategy() {
        return quranPageFragmentStrategy;
    }

    public Madani15QuranDualPageFragmentStrategy getQuranDualPageFragmentStrategy() {
        return quranDualPageFragmentStrategy;
    }
}
