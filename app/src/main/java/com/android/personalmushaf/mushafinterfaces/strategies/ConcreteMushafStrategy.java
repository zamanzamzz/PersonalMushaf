package com.android.personalmushaf.mushafinterfaces.strategies;

import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies.ConcreteJuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies.ConcreteJuzStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies.ConcreteNavigationActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies.ConcreteRukuContentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies.ConcreteSurahStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies.ConcreteQuranActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies.ConcreteQuranDualPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies.ConcreteQuranPageAdapterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.concretequranstrategies.ConcreteQuranPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageFragmentStrategy;

public class ConcreteMushafStrategy implements MushafStrategy {
    private JuzQuarterStrategy juzQuarterStrategy;
    private JuzStrategy juzStrategy;
    private NavigationActivityStrategy navigationActivityStrategy;
    private RukuContentStrategy rukuContentStrategy;
    private SurahStrategy surahStrategy;

    private QuranActivityStrategy quranActivityStrategy;
    private QuranPageAdapterStrategy quranPageAdapterStrategy;
    private QuranPageFragmentStrategy quranPageFragmentStrategy;
    private QuranDualPageFragmentStrategy quranDualPageFragmentStrategy;

    public ConcreteMushafStrategy(MushafMetadata mushafMetadata) {
        juzQuarterStrategy = new ConcreteJuzQuarterStrategy(mushafMetadata);
        juzStrategy = new ConcreteJuzStrategy(mushafMetadata);
        navigationActivityStrategy = new ConcreteNavigationActivityStrategy(mushafMetadata);
        rukuContentStrategy = new ConcreteRukuContentStrategy(mushafMetadata);
        surahStrategy = new ConcreteSurahStrategy(mushafMetadata);

        quranActivityStrategy = new ConcreteQuranActivityStrategy(mushafMetadata);
        quranPageAdapterStrategy = new ConcreteQuranPageAdapterStrategy(mushafMetadata);
        quranPageFragmentStrategy = new ConcreteQuranPageFragmentStrategy(mushafMetadata);
        quranDualPageFragmentStrategy = new ConcreteQuranDualPageFragmentStrategy(mushafMetadata);
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

    public QuranActivityStrategy getQuranActivityStrategy() {
        return quranActivityStrategy;
    }

    public QuranPageAdapterStrategy getQuranPageAdapterStrategy() {
        return quranPageAdapterStrategy;
    }

    public QuranPageFragmentStrategy getQuranPageFragmentStrategy() {
        return quranPageFragmentStrategy;
    }

    public QuranDualPageFragmentStrategy getQuranDualPageFragmentStrategy() {
        return quranDualPageFragmentStrategy;
    }
}
