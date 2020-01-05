package com.android.naskh15;


import com.android.naskh15.navigationstrategies.Naskh15JuzQuarterStrategy;
import com.android.naskh15.navigationstrategies.Naskh15JuzStrategy;
import com.android.naskh15.navigationstrategies.Naskh15NavigationActivityStrategy;
import com.android.naskh15.navigationstrategies.Naskh15RukuContentStrategy;
import com.android.naskh15.navigationstrategies.Naskh15SurahStrategy;
import com.android.naskh15.quranstrategies.Naskh15QuranActivityStrategy;
import com.android.naskh15.quranstrategies.Naskh15QuranDualPageFragmentStrategy;
import com.android.naskh15.quranstrategies.Naskh15QuranPageAdapterStrategy;
import com.android.naskh15.quranstrategies.Naskh15QuranPageFragmentStrategy;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.mushafinterfaces.strategies.MushafStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;

public class Naskh15Strategy implements MushafStrategy {

    private MushafMetadata mushafMetadata;
    private Naskh15NavigationActivityStrategy navigationActivityStrategy;
    private Naskh15JuzQuarterStrategy juzQuarterStrategy;
    private Naskh15JuzStrategy juzStrategy;
    private Naskh15RukuContentStrategy rukuContentStrategy;
    private Naskh15SurahStrategy surahStrategy;

    private Naskh15QuranActivityStrategy quranActivityStrategy;
    private Naskh15QuranPageAdapterStrategy quranPageAdapterStrategy;
    private Naskh15QuranPageFragmentStrategy quranPageFragmentStrategy;
    private Naskh15QuranDualPageFragmentStrategy quranDualPageFragmentStrategy;

    public Naskh15Strategy() {
        mushafMetadata = MushafMetadataFactory.getMushafMetadata(QuranSettings.CLASSIC_NASKH_15_LINE);
        navigationActivityStrategy = new Naskh15NavigationActivityStrategy();
        juzQuarterStrategy = new Naskh15JuzQuarterStrategy();
        juzStrategy = new Naskh15JuzStrategy();
        rukuContentStrategy = new Naskh15RukuContentStrategy();
        surahStrategy = new Naskh15SurahStrategy();

        quranActivityStrategy = new Naskh15QuranActivityStrategy();
        quranPageAdapterStrategy = new Naskh15QuranPageAdapterStrategy();
        quranPageFragmentStrategy = new Naskh15QuranPageFragmentStrategy(mushafMetadata);
        quranDualPageFragmentStrategy = new Naskh15QuranDualPageFragmentStrategy(mushafMetadata);
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

    public Naskh15QuranActivityStrategy getQuranActivityStrategy() {
        return quranActivityStrategy;
    }

    public Naskh15QuranPageAdapterStrategy getQuranPageAdapterStrategy() {
        return quranPageAdapterStrategy;
    }

    public Naskh15QuranPageFragmentStrategy getQuranPageFragmentStrategy() {
        return quranPageFragmentStrategy;
    }

    public Naskh15QuranDualPageFragmentStrategy getQuranDualPageFragmentStrategy() {
        return quranDualPageFragmentStrategy;
    }
}
