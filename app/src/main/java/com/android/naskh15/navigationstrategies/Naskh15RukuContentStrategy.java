package com.android.naskh15.navigationstrategies;

import android.view.View;

import com.android.naskh15.Naskh15NavigationData;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentAdapter;

public class Naskh15RukuContentStrategy implements RukuContentStrategy {
    public RukuContentAdapter getRukuContentAdapter(int juzNumber, View v) {
        int[][] rukuInfo = QuranConstants.rukuInfo[(juzNumber - 1)];
        int[] rukuPageNumbers = Naskh15NavigationData.naskh15RukuPageNumbers[juzNumber - 1];
        double[] rukuLengths = Naskh15NavigationData.naskh15RukuLengths[juzNumber - 1];
        String[] prefixes = v.getResources().getStringArray(v.getResources().getIdentifier("juz_" + ((juzNumber - 1)), "array", v.getContext().getPackageName()));
        return new RukuContentAdapter(rukuInfo, rukuPageNumbers, rukuLengths, prefixes);
    }
}
