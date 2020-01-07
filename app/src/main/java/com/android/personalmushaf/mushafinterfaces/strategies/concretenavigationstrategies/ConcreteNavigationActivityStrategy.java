package com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies;

import android.content.Context;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.navigation.ViewPagerAdapter;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;

public class ConcreteNavigationActivityStrategy implements NavigationActivityStrategy {
    private MushafMetadata mushafMetadata;

    public ConcreteNavigationActivityStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public double getJuzLength(int juzNumber) {
        return mushafMetadata.getNavigationData().getJuzLengths()[juzNumber - 1];
    }

    public void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment, Context context) {
        int landmarkSystem = QuranSettings.getInstance().getLandMarkSystem(context);
        if (mushafMetadata.getShouldDoRuku(landmarkSystem))
            viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
    }
}
