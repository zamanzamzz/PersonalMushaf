package com.android.naskh13.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentAdapter;

public class Naskh13RukuContentStrategy implements RukuContentStrategy {
    public RukuContentAdapter getRukuContentAdapter(int juzNumber, View v) {
        int[][] dataset = Naskh13NavigationData.rukuInfo[(juzNumber - 1)];
        String[] prefixes = v.getResources().getStringArray(v.getResources().getIdentifier("juz_" + ((juzNumber - 1)), "array", v.getContext().getPackageName()));
        return new RukuContentAdapter(dataset, prefixes);
    }
}
