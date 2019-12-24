package com.android.naskh13.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.surahtab.SurahAdapter;

public class Naskh13SurahStrategy implements SurahStrategy {
    public SurahAdapter getSurahAdapter(int juzNumber, View v) {
        int[][] dataset = getSurahsInJuz(juzNumber);
        String[] prefixes = new String[dataset.length];

        if (dataset.length > 0) {
            int firstSurahInJuz;
            if (juzNumber > 0)
                firstSurahInJuz = getFirstSurahInJuz(juzNumber);
            else
                firstSurahInJuz = 0;

            System.arraycopy(v.getResources().getStringArray(R.array.surah_names), firstSurahInJuz, prefixes, 0, dataset.length);
        }

        return new SurahAdapter(dataset, prefixes);
    }

    private int[][] getSurahsInJuz(int juzNumber) {
        return QuranConstants.getSurahsInJuz(Naskh13NavigationData.surahInfo, juzNumber);
    }

    private int getFirstSurahInJuz(int juzNumber) {
        return QuranConstants.getFirstSurahInJuz(juzNumber);
    }
}
