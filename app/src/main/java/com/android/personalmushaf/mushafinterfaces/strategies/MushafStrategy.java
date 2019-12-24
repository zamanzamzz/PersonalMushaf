package com.android.personalmushaf.mushafinterfaces.strategies;

import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranActivityStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageFragmentStrategy;

public interface MushafStrategy {
    JuzQuarterStrategy getJuzQuarterStrategy();

    JuzStrategy getJuzStrategy();

    NavigationActivityStrategy getNavigationActivityStrategy();

    RukuContentStrategy getRukuContentStrategy();

    SurahStrategy getSurahStrategy();

    QuranActivityStrategy getQuranActivityStrategy();

    QuranPageAdapterStrategy getQuranPageAdapterStrategy();

    QuranPageFragmentStrategy getQuranPageFragmentStrategy();

    QuranDualPageFragmentStrategy getQuranDualPageFragmentStrategy();
}
