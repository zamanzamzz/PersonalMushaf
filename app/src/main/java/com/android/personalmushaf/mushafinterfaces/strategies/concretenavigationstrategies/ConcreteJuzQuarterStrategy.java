package com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies;

import android.content.Context;
import android.view.View;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractJuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class ConcreteJuzQuarterStrategy extends AbstractJuzQuarterStrategy implements JuzQuarterStrategy {
    private MushafMetadata mushafMetadata;

    public ConcreteJuzQuarterStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v, Context context) {
        int landmarkSystem = QuranSettings.getInstance().getLandMarkSystem(context);
        int[][] info;
        int[] pageNumbers;
        String[] prefixes;
        double[] lengths;

        if (mushafMetadata.getShouldDoRuku(landmarkSystem)) {
            info = getQuarterInfo(juzNumber, QuranConstants.naskhQuarterInfo, RUKUNUMOFQUARTERS);
            pageNumbers = getQuarterPageNumbers(juzNumber, mushafMetadata.getNavigationData().getQuarterPageNumbers(), RUKUNUMOFQUARTERS);
            prefixes = getQuarterStringResource(juzNumber, v.getResources().getStringArray(R.array.naskh_quarter_prefixes), RUKUNUMOFQUARTERS);
            lengths = getQuarterLengths(juzNumber, mushafMetadata.getNavigationData().getQuarterLengths(), RUKUNUMOFQUARTERS);
        } else  {
            info = getQuarterInfo(juzNumber, QuranConstants.madaniHizbInfo, MADANINUMOFQUARTERS);
            pageNumbers = getQuarterPageNumbers(juzNumber, mushafMetadata.getNavigationData().getHizbPageNumbers(), MADANINUMOFQUARTERS);
            prefixes = getQuarterStringResource(juzNumber, v.getResources().getStringArray(R.array.madani_quarter_prefixes), MADANINUMOFQUARTERS);
            lengths = getQuarterLengths(juzNumber, mushafMetadata.getNavigationData().getHizbLengths(), MADANINUMOFQUARTERS);
        }

        return new JuzQuarterAdapter(info, pageNumbers, prefixes, lengths);
    }
}