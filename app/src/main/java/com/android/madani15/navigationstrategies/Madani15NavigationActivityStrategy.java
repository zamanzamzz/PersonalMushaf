package com.android.madani15.navigationstrategies;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class Madani15NavigationActivityStrategy implements NavigationActivityStrategy {
    public int getJuzLength(int juzNumber) {
        return Madani15NavigationData.juzInfo[juzNumber-1][0];
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment) {

    }
}
