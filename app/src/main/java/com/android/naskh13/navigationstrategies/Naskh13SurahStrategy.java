package com.android.naskh13.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractSurahStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;
import com.android.personalmushaf.navigation.tabs.surahtab.SurahAdapter;

public class Naskh13SurahStrategy extends AbstractSurahStrategy implements SurahStrategy {

    public SurahAdapter getSurahAdapter(int juzNumber, View v) {
        int[][] surahInfo = getSurahInfoInJuz(juzNumber);
        int[] surahPageNumbersInJuz = getSurahPageNumbersinJuz(Naskh13NavigationData.naskh13SurahPageNumbers, juzNumber);
        String[] surahNamesInJuz = getSurahNamesInJuz(juzNumber, surahInfo.length, v.getResources().getStringArray(R.array.surah_names));

        return new SurahAdapter(surahInfo, surahPageNumbersInJuz, surahNamesInJuz);
    }

}
