package com.android.madani15;

import android.view.View;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.NavigationStrategy;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class Madani15NavigationStrategy implements NavigationStrategy {
    public int getJuzLength(int juzNumber) {
        return Madani15NavigationData.juzInfo[juzNumber-1][0];
    }

    public int[][] getJuzInfo() {
        return Madani15NavigationData.juzInfo;
    }

    public int[][] getSurahsInJuz(int juzNumber) {
        return QuranConstants.getSurahsInJuz(Madani15NavigationData.surahInfo, juzNumber);
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment) {

    }

    public JuzQuarterAdapter getJuzQuarterAdapter(int juzIndex, View v) {
        int[][] dataset = new int[8][4];
        String[] prefixes = new String[8];
        System.arraycopy(Madani15NavigationData.madaniQuarterInfo, juzIndex *8, dataset, 0, 8);
        System.arraycopy(v.getResources().getStringArray(R.array.madani_quarter_prefixes), juzIndex*8, prefixes, 0, 8);

        return new JuzQuarterAdapter(dataset, prefixes, null);
    }

    public RukuContentAdapter getRukuContentAdapter(int juzIndex, View v) {
        return null;
    }
}
