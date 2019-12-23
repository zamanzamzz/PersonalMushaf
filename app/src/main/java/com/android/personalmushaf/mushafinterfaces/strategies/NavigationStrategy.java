package com.android.personalmushaf.mushafinterfaces.strategies;

import android.view.View;

import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public interface NavigationStrategy {
    int getJuzLength(int juzNumber);

    int[][] getJuzInfo();

    int[][] getSurahsInJuz(int juzNumber);

    void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment);

    JuzQuarterAdapter getJuzQuarterAdapter(int juzIndex, View v);

    RukuContentAdapter getRukuContentAdapter(int juzIndex, View v);
}
