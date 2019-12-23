package com.android.personalmushaf.model.mushafs.strategies.navigationstrategies;

import android.view.View;

import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public interface NavigationStrategy {
    int getJuzLengthIndex();

    int getJuzPageNumberIndex();

    int getSurahPageNumberIndex();

    void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment);

    JuzQuarterAdapter getJuzQuarterAdapter(int juzIndex, View v);
}
