package com.android.madani15.navigationstrategies;

import android.view.View;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractSurahStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;
import com.android.personalmushaf.navigation.tabs.surahtab.SurahAdapter;

public class Madani15SurahStrategy extends AbstractSurahStrategy implements SurahStrategy {

    public SurahAdapter getSurahAdapter(int juzNumber, View v) {
        int[][] surahInfo = getSurahInfoInJuz(juzNumber);
        int[] surahPageNumbersInJuz = getSurahPageNumbersinJuz(Madani15NavigationData.madani15SurahPageNumbers, juzNumber);
        String[] surahNamesInJuz = getSurahNamesInJuz(juzNumber, surahInfo.length, v.getResources().getStringArray(R.array.surah_names));
        double[] surahLengthsInJuz = getSurahLengthsInJuz(Madani15NavigationData.madani15SurahLengths, juzNumber);

        return new SurahAdapter(surahInfo, surahPageNumbersInJuz, surahNamesInJuz, surahLengthsInJuz);
    }

}
