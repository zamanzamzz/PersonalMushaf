package com.android.madani15.navigationstrategies;

import android.view.View;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractJuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class Madani15JuzQuarterStrategy extends AbstractJuzQuarterStrategy implements JuzQuarterStrategy {
    private static final int NUMOFQUARTERS = 8;
    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v) {
        int[][] hizbInfo      = getQuarterInfo(juzNumber, QuranConstants.madaniHizbInfo, NUMOFQUARTERS);
        int[] hizbPageNumbers = getQuarterPageNumbers(juzNumber, Madani15NavigationData.madani15HizbPageNumbers, NUMOFQUARTERS);
        String[] prefixes     = getQuarterStringResource(juzNumber, v.getResources().getStringArray(R.array.madani_quarter_prefixes), NUMOFQUARTERS);
        return new JuzQuarterAdapter(hizbInfo, hizbPageNumbers, prefixes, null);
    }
}
