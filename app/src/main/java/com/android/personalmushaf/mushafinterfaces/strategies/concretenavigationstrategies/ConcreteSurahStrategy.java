package com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies;

import android.view.View;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractSurahStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;
import com.android.personalmushaf.navigation.tabs.surahtab.SurahAdapter;

public class ConcreteSurahStrategy extends AbstractSurahStrategy implements SurahStrategy{
    private MushafMetadata mushafMetadata;

    public ConcreteSurahStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public SurahAdapter getSurahAdapter(int juzNumber, View v) {
        int[][] surahInfo = getSurahInfoInJuz(juzNumber);
        int[] surahPageNumbersInJuz = getSurahPageNumbersinJuz(mushafMetadata.getNavigationData().getSurahPageNumbers(), juzNumber);
        String[] surahNamesInJuz = getSurahNamesInJuz(juzNumber, surahInfo.length, v.getResources().getStringArray(R.array.surah_names));
        double[] surahLengthsInJuz = getSurahLengthsInJuz(mushafMetadata.getNavigationData().getSurahLengths(), juzNumber);

        return new SurahAdapter(surahInfo, surahPageNumbersInJuz, surahNamesInJuz, surahLengthsInJuz);    }
}
