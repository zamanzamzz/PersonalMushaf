package com.android.naskh13.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class Naskh13JuzQuarterStrategy implements JuzQuarterStrategy {
    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v) {
        int[][] quarterInfo = getQuarterInfo(juzNumber);
        int[] pageNumbers = getQuarterPageNumbers(juzNumber);
        String[] prefixes = getPrefixes(juzNumber, v);
        String[] lengths = getQuarterLengths(juzNumber, v);
        return new JuzQuarterAdapter(quarterInfo, pageNumbers, prefixes, lengths);
    }

    private int[][] getQuarterInfo(int juzNumber) {
        int[][] quarterInfo = new int[4][3];
        System.arraycopy(QuranConstants.naskhQuarterInfo, (juzNumber - 1) *4, quarterInfo, 0, 4);
        return quarterInfo;
    }

    private int[] getQuarterPageNumbers(int juzNumber) {
        int[] pageNumbers = new int[4];
        System.arraycopy(Naskh13NavigationData.naskh13QuarterPageNumbers, (juzNumber - 1) *4, pageNumbers, 0, 4);
        return pageNumbers;
    }

    private String[] getPrefixes(int juzNumber, View v) {
        String[] prefixes = new String[4];
        System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_prefixes), (juzNumber - 1)*4, prefixes, 0, 4);
        return prefixes;
    }

    private String[] getQuarterLengths(int juzNumber, View v) {
        String[] lengths = new String[4];
        System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_lengths), (juzNumber - 1)*4, lengths, 0, 4);
        return lengths;
    }
}
