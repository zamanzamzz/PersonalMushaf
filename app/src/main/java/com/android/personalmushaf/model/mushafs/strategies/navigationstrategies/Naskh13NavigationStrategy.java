package com.android.personalmushaf.model.mushafs.strategies.navigationstrategies;

import android.view.View;

import com.android.personalmushaf.R;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class Naskh13NavigationStrategy implements NavigationStrategy {
    public int getJuzLengthIndex() {
        return 0;
    }

    public int getJuzPageNumberIndex() {
        return 2;
    }

    public int getSurahPageNumberIndex() {
        return 4;
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment) {
        viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
    }

    public JuzQuarterAdapter getJuzQuarterAdapter(int juzIndex, View v) {
        int[][] dataset = new int[4][4];
        String[] prefixes = new String[4];
        String[] lengths = new String[4];
        System.arraycopy(QuranConstants.naskhQuarterInfo, juzIndex *4, dataset, 0, 4);
        System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_prefixes), juzIndex*4, prefixes, 0, 4);
        System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_lengths), juzIndex*4, lengths, 0, 4);

        return new JuzQuarterAdapter(dataset, prefixes, lengths);
    }
}
