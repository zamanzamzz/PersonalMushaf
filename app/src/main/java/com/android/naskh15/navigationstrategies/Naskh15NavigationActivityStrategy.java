package com.android.naskh15.navigationstrategies;

import android.content.Context;

import com.android.naskh15.Naskh15NavigationData;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class Naskh15NavigationActivityStrategy implements NavigationActivityStrategy {
    public double getJuzLength(int juzNumber) {
        return Naskh15NavigationData.naskh15JuzLengths[juzNumber - 1];
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment, Context context) {
        int landmarkSystem = QuranSettings.getInstance().getLandMarkSystem(context);
        if (landmarkSystem == QuranSettings.DEFAULT_LANDMARK_SYSTEM || landmarkSystem == QuranSettings.RUKU)
            viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
    }
}
