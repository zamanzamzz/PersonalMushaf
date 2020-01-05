package com.android.naskh13;

import com.android.naskh13.navigationstrategies.Naskh13JuzQuarterStrategy;
import com.android.naskh13.navigationstrategies.Naskh13JuzStrategy;
import com.android.naskh13.navigationstrategies.Naskh13NavigationActivityStrategy;
import com.android.naskh13.navigationstrategies.Naskh13RukuContentStrategy;
import com.android.naskh13.navigationstrategies.Naskh13SurahStrategy;
import com.android.naskh13.quranstrategies.Naskh13QuranActivityStrategy;
import com.android.naskh13.quranstrategies.Naskh13QuranDualPageFragmentStrategy;
import com.android.naskh13.quranstrategies.Naskh13QuranPageAdapterStrategy;
import com.android.naskh13.quranstrategies.Naskh13QuranPageFragmentStrategy;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.mushafinterfaces.strategies.MushafStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;

public class Naskh13Strategy implements MushafStrategy {

    private MushafMetadata mushafMetadata;
    private Naskh13NavigationActivityStrategy navigationActivityStrategy;
    private Naskh13JuzQuarterStrategy juzQuarterStrategy;
    private Naskh13JuzStrategy juzStrategy;
    private Naskh13RukuContentStrategy rukuContentStrategy;
    private Naskh13SurahStrategy surahStrategy;

    private Naskh13QuranActivityStrategy quranActivityStrategy;
    private Naskh13QuranPageAdapterStrategy quranPageAdapterStrategy;
    private Naskh13QuranPageFragmentStrategy quranPageFragmentStrategy;
    private Naskh13QuranDualPageFragmentStrategy quranDualPageFragmentStrategy;

    public Naskh13Strategy() {
        mushafMetadata = MushafMetadataFactory.getMushafMetadata(QuranSettings.MODERN_NASKH_13_LINE);
        navigationActivityStrategy = new Naskh13NavigationActivityStrategy();
        juzQuarterStrategy = new Naskh13JuzQuarterStrategy();
        juzStrategy = new Naskh13JuzStrategy();
        rukuContentStrategy = new Naskh13RukuContentStrategy();
        surahStrategy = new Naskh13SurahStrategy();

        quranActivityStrategy = new Naskh13QuranActivityStrategy();
        quranPageAdapterStrategy = new Naskh13QuranPageAdapterStrategy();
        quranPageFragmentStrategy = new Naskh13QuranPageFragmentStrategy(mushafMetadata);
        quranDualPageFragmentStrategy = new Naskh13QuranDualPageFragmentStrategy(mushafMetadata);
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

    public Naskh13QuranActivityStrategy getQuranActivityStrategy() {
        return quranActivityStrategy;
    }

    public Naskh13QuranPageAdapterStrategy getQuranPageAdapterStrategy() {
        return quranPageAdapterStrategy;
    }

    public Naskh13QuranPageFragmentStrategy getQuranPageFragmentStrategy() {
        return quranPageFragmentStrategy;
    }

    public Naskh13QuranDualPageFragmentStrategy getQuranDualPageFragmentStrategy() {
        return quranDualPageFragmentStrategy;
    }
}
