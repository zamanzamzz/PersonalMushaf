package com.android.naskh13.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class Naskh13JuzQuarterStrategy implements JuzQuarterStrategy {
    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v) {
        int[][] dataset = new int[4][4];
        String[] prefixes = new String[4];
        String[] lengths = new String[4];
        System.arraycopy(Naskh13NavigationData.naskhQuarterInfo, (juzNumber - 1) *4, dataset, 0, 4);
        System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_prefixes), (juzNumber - 1)*4, prefixes, 0, 4);
        System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_lengths), (juzNumber - 1)*4, lengths, 0, 4);

        return new JuzQuarterAdapter(dataset, prefixes, lengths);
    }
}
