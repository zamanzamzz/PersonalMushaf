package com.android.naskh13;

import android.view.View;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.NavigationStrategy;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class Naskh13NavigationStrategy implements NavigationStrategy {
    public int getJuzLength(int juzNumber) {
        return Naskh13NavigationData.juzInfo[juzNumber-1][0];
    }

    public int[][] getJuzInfo() {
        return Naskh13NavigationData.juzInfo;
    }

    public int[][] getSurahsInJuz(int juzNumber) {
        return QuranConstants.getSurahsInJuz(Naskh13NavigationData.surahInfo, juzNumber);
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment) {
        viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
    }

    public JuzQuarterAdapter getJuzQuarterAdapter(int juzIndex, View v) {
        int[][] dataset = new int[4][4];
        String[] prefixes = new String[4];
        String[] lengths = new String[4];
        System.arraycopy(Naskh13NavigationData.naskhQuarterInfo, juzIndex *4, dataset, 0, 4);
        System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_prefixes), juzIndex*4, prefixes, 0, 4);
        System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_lengths), juzIndex*4, lengths, 0, 4);

        return new JuzQuarterAdapter(dataset, prefixes, lengths);
    }

    public RukuContentAdapter getRukuContentAdapter(int juzIndex, View v) {
        int[][] dataset = Naskh13NavigationData.rukuInfo[juzIndex];
        String[] prefixes = v.getResources().getStringArray(v.getResources().getIdentifier("juz_" + (juzIndex), "array", v.getContext().getPackageName()));
        return new RukuContentAdapter(dataset, prefixes);
    }
}
