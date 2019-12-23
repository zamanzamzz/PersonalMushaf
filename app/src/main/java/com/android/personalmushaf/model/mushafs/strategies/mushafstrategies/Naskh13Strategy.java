package com.android.personalmushaf.model.mushafs.strategies.mushafstrategies;

import com.android.personalmushaf.model.mushafs.strategies.navigationstrategies.Naskh13NavigationStrategy;
import com.android.personalmushaf.model.mushafs.strategies.navigationstrategies.NavigationStrategy;
import com.android.personalmushaf.model.mushafs.strategies.quranstrategies.Naskh13QuranStrategy;
import com.android.personalmushaf.model.mushafs.strategies.quranstrategies.QuranStrategy;

public class Naskh13Strategy implements MushafStrategy {

    private QuranStrategy quranStrategy;
    private NavigationStrategy navigationStrategy;

    public Naskh13Strategy() {
        quranStrategy = new Naskh13QuranStrategy();
        navigationStrategy = new Naskh13NavigationStrategy();
    }

    public QuranStrategy getQuranStrategy() {
        return quranStrategy;
    }

    public NavigationStrategy getNavivationStrategy() {
        return navigationStrategy;
    }
}
