package com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies;

import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public interface NavigationActivityStrategy {
    double getJuzLength(int juzNumber);

    void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment);
}
