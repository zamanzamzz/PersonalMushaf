package com.android.personalmushaf.mushafinterfaces.strategies;

import com.android.madani15.Madani15Strategy;
import com.android.naskh13.Naskh13Strategy;
import com.android.naskh15.Naskh15Strategy;
import com.android.personalmushaf.QuranSettings;

public class MushafStrategyFactory {
    public static MushafStrategy getMushafStrategy(int mushaf) {
        switch (mushaf) {
            case QuranSettings.MODERN_NASKH_13_LINE:
                return new Naskh13Strategy();
            case QuranSettings.CLASSIC_NASKH_15_LINE:
                return new Naskh15Strategy();
            case QuranSettings.CLASSIC_MADANI_15_LINE:
                return new Madani15Strategy();
            default:
                return null;
        }
    }
}
