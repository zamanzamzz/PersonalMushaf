package com.android.madani15.navigationstrategies;

import android.view.View;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class Madani15JuzQuarterStrategy implements JuzQuarterStrategy {
    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v) {
        int[][] dataset = new int[8][4];
        String[] prefixes = new String[8];
        System.arraycopy(Madani15NavigationData.madaniQuarterInfo, (juzNumber - 1) * 8, dataset, 0, 8);
        System.arraycopy(v.getResources().getStringArray(R.array.madani_quarter_prefixes), (juzNumber - 1)*8, prefixes, 0, 8);

        return new JuzQuarterAdapter(dataset, prefixes, null);
    }
}
