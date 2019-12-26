package com.android.personalmushaf.quranpage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.android.personalmushaf.QuranActivity;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;

public class QuranPageAdapter extends FragmentStateAdapter {

    private QuranPageAdapterStrategy quranPageAdapterStrategy;
    private int orientation;
    private boolean isForceDualPage;

    public QuranPageAdapter(FragmentManager fm, Lifecycle lifecycle, QuranPageAdapterStrategy quranPageAdapterStrategy,
                            Context context, int orientation) {
        super(fm, lifecycle);
        this.quranPageAdapterStrategy = quranPageAdapterStrategy;
        isForceDualPage = QuranSettings.getInstance().getIsForceDualPage(context);
        this.orientation = orientation;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        Bundle bundle;

        if (QuranActivity.isLandscape(orientation) || isForceDualPage) {
            fragment = new QuranDualPageFragment();
            bundle = new Bundle();
            bundle.putInt("dual_pager_position", position);
            fragment.setArguments(bundle);
        } else {
            int pageNumber = quranPageAdapterStrategy.getPageNumberFromPagerPosition(position);
            fragment = new QuranPageFragment();
            bundle = new Bundle();
            bundle.putInt("page_number", pageNumber);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        if (!QuranActivity.isLandscape(orientation) && !isForceDualPage) {
            return quranPageAdapterStrategy.getNumOfSinglePages();
        } else {
            return quranPageAdapterStrategy.getNumOfDualPages();
        }
    }
}
