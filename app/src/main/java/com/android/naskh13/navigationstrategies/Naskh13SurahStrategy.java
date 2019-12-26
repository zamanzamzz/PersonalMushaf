package com.android.naskh13.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.surahtab.SurahAdapter;

public class Naskh13SurahStrategy implements SurahStrategy {
    public SurahAdapter getSurahAdapter(int juzNumber, View v) {
        int[][] surahInfo = getSurahInfoInJuz(juzNumber);
        int[] surahPageNumbersInJuz = getSurahPageNumbersInJuz(juzNumber);
        String[] prefixes = getSurahPrefixesInJuz(juzNumber, surahInfo.length, v);

        return new SurahAdapter(surahInfo, surahPageNumbersInJuz, prefixes);
    }

    private int[][] getSurahInfoInJuz(int juzNumber) {
        return QuranConstants.getSurahsInJuz(QuranConstants.surahInfo, juzNumber);
    }

    private int[] getSurahPageNumbersInJuz(int juzNumber) {
        return QuranConstants.getSurahPageNumbersinJuz(Naskh13NavigationData.naskh13SurahPageNumbers, juzNumber);
    }

    private int getFirstSurahInJuz(int juzNumber) {
        return QuranConstants.getFirstSurahInJuz(juzNumber);
    }

    private String[] getSurahPrefixesInJuz(int juzNumber, int length, View v) {
        String[] prefixes = new String[length];

        if (length > 0) {
            int firstSurahInJuz;
            if (juzNumber > 0)
                firstSurahInJuz = getFirstSurahInJuz(juzNumber);
            else
                firstSurahInJuz = 0;

            System.arraycopy(v.getResources().getStringArray(R.array.surah_names), firstSurahInJuz, prefixes, 0, length);
        }

        return prefixes;
    }
}
