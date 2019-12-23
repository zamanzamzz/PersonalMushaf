package com.android.personalmushaf.model.mushafs.strategies.navigationstrategies;

import android.view.View;

import com.android.personalmushaf.R;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class Madani15NavigationStrategy implements NavigationStrategy {
    public int getJuzLengthIndex() {
        return 1;
    }

    public int getJuzPageNumberIndex() {
        return 3;
    }

    public int getSurahPageNumberIndex() {
        return 3;
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment) {

    }

    public JuzQuarterAdapter getJuzQuarterAdapter(int juzIndex, View v) {
        int[][] dataset = new int[8][4];
        String[] prefixes = new String[8];
        System.arraycopy(QuranConstants.madaniQuarterInfo, juzIndex *8, dataset, 0, 8);
        System.arraycopy(v.getResources().getStringArray(R.array.madani_quarter_prefixes), juzIndex*8, prefixes, 0, 8);

        return new JuzQuarterAdapter(dataset, prefixes, null);
    }
}
