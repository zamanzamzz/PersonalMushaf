package com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies;

import android.view.View;

import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentAdapter;

public class ConcreteRukuContentStrategy implements RukuContentStrategy {
    private MushafMetadata mushafMetadata;

    public ConcreteRukuContentStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public RukuContentAdapter getRukuContentAdapter(int juzNumber, View v) {
        int[][] rukuInfo = QuranConstants.rukuInfo[(juzNumber - 1)];
        int[] rukuPageNumbers = mushafMetadata.getNavigationData().getRukuPageNumbers()[juzNumber - 1];
        double[] rukuLengths = mushafMetadata.getNavigationData().getRukuLengths()[juzNumber - 1];
        String[] prefixes = v.getResources().getStringArray(v.getResources().getIdentifier("juz_" + ((juzNumber - 1)), "array", v.getContext().getPackageName()));
        return new RukuContentAdapter(rukuInfo, rukuPageNumbers, rukuLengths, prefixes);
    }
}
