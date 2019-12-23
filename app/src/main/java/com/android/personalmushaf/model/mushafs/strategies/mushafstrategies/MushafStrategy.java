package com.android.personalmushaf.model.mushafs.strategies.mushafstrategies;

import com.android.personalmushaf.model.mushafs.strategies.navigationstrategies.NavigationStrategy;
import com.android.personalmushaf.model.mushafs.strategies.quranstrategies.QuranStrategy;

public interface MushafStrategy {

    QuranStrategy getQuranStrategy();

    NavigationStrategy getNavivationStrategy();


}
