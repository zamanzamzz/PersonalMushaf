package com.android.naskh13.navigationstrategies;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class Naskh13NavigationActivityStrategy implements NavigationActivityStrategy {
    public int getJuzLength(int juzNumber) {
        return Naskh13NavigationData.naskh13JuzLengths[juzNumber - 1];
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment) {
        viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
    }
}
