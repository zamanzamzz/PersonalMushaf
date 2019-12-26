package com.android.madani15.navigationstrategies;

import android.view.View;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class Madani15JuzQuarterStrategy implements JuzQuarterStrategy {
    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v) {
        int[][] hizbInfo = getHizbInfo(juzNumber);
        int[] hizbPageNumbers = getHizbPageNumbers(juzNumber);
        String[] prefixes = getPrefixes(juzNumber, v);

        return new JuzQuarterAdapter(hizbInfo, hizbPageNumbers, prefixes, null);
    }

    private int[][] getHizbInfo(int juzNumber) {
        int[][] hizbInfo = new int[8][3];
        System.arraycopy(QuranConstants.madaniHizbInfo, (juzNumber - 1) * 8, hizbInfo, 0, 8);
        return hizbInfo;
    }

    private int[] getHizbPageNumbers(int juzNumber) {
        int[] hizbPageNumbers = new int[8];
        System.arraycopy(Madani15NavigationData.madani15HizbPageNumbers, (juzNumber - 1) * 8, hizbPageNumbers, 0, 8);
        return hizbPageNumbers;
    }

    private String[] getPrefixes(int juzNumber, View v) {
        String[] prefixes = new String[8];
        System.arraycopy(v.getResources().getStringArray(R.array.madani_quarter_prefixes), (juzNumber - 1)*8, prefixes, 0, 8);
        return prefixes;
    }
}
