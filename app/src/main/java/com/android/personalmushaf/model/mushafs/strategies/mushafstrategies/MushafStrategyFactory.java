package com.android.personalmushaf.model.mushafs.strategies.mushafstrategies;

import com.android.personalmushaf.QuranSettings;

public class MushafStrategyFactory {
    public static MushafStrategy getMushafStrategy(int mushaf) {
        switch (mushaf) {
            case QuranSettings.NASKH13LINE:
                return new Naskh13Strategy();
            case QuranSettings.NASKH15LINE:
                return null;
            case QuranSettings.MADANI15LINE:
                return new Madani15Strategy();
            default:
                return null;
        }
    }
}
