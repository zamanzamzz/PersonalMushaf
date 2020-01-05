package com.android.madani15.navigationstrategies;

import android.content.Context;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class Madani15NavigationActivityStrategy implements NavigationActivityStrategy {
    public double getJuzLength(int juzNumber) {
        return Madani15NavigationData.madani15JuzLengths[juzNumber - 1];
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment, Context context) {
        int landmarkSystem = QuranSettings.getInstance().getLandMarkSystem(context);
        if (landmarkSystem == QuranSettings.RUKU)
            viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
    }
}
