package com.android.naskh15.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.naskh15.Naskh15NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractSurahStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;
import com.android.personalmushaf.navigation.tabs.surahtab.SurahAdapter;

public class Naskh15SurahStrategy extends AbstractSurahStrategy implements SurahStrategy {

    public SurahAdapter getSurahAdapter(int juzNumber, View v) {
        int[][] surahInfo = getSurahInfoInJuz(juzNumber);
        int[] surahPageNumbersInJuz = getSurahPageNumbersinJuz(Naskh15NavigationData.naskh15SurahPageNumbers, juzNumber);
        String[] surahNamesInJuz = getSurahNamesInJuz(juzNumber, surahInfo.length, v.getResources().getStringArray(R.array.surah_names));
        double[] surahLengthsInJuz = getSurahLengthsInJuz(Naskh15NavigationData.naskh15SurahLengths, juzNumber);

        return new SurahAdapter(surahInfo, surahPageNumbersInJuz, surahNamesInJuz, surahLengthsInJuz);
    }

}
