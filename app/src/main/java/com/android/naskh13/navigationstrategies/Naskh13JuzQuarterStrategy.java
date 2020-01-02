package com.android.naskh13.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractJuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class Naskh13JuzQuarterStrategy extends AbstractJuzQuarterStrategy implements JuzQuarterStrategy {
    private static final int NUMOFQUARTERS = 4;
    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v) {
        int[][] quarterInfo = getQuarterInfo(juzNumber, QuranConstants.naskhQuarterInfo, NUMOFQUARTERS);
        int[] pageNumbers   = getQuarterPageNumbers(juzNumber, Naskh13NavigationData.naskh13QuarterPageNumbers, NUMOFQUARTERS);
        String[] prefixes   = getQuarterStringResource(juzNumber, v.getResources().getStringArray(R.array.naskh_quarter_prefixes), NUMOFQUARTERS);
        double[] lengths    = getQuarterLengths(juzNumber, Naskh13NavigationData.naskhQuarterLengths, NUMOFQUARTERS);
        return new JuzQuarterAdapter(quarterInfo, pageNumbers, prefixes, lengths);
    }
}
