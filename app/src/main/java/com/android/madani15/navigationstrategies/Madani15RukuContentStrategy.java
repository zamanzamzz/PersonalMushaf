package com.android.madani15.navigationstrategies;

import android.view.View;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentAdapter;

public class Madani15RukuContentStrategy implements RukuContentStrategy {
    public RukuContentAdapter getRukuContentAdapter(int juzNumber, View v) {
        int[][] rukuInfo = QuranConstants.rukuInfo[(juzNumber - 1)];
        int[] rukuPageNumbers = Madani15NavigationData.madani15RukuPageNumbers[juzNumber - 1];
        double[] rukuLengths = Madani15NavigationData.madani15RukuLengths[juzNumber - 1];
        String[] prefixes = v.getResources().getStringArray(v.getResources().getIdentifier("juz_" + ((juzNumber - 1)), "array", v.getContext().getPackageName()));
        return new RukuContentAdapter(rukuInfo, rukuPageNumbers, rukuLengths, prefixes);
    }
}