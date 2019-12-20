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
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;

public class QuranPageAdapter extends FragmentStateAdapter {

    private int mushafVersion;
    private int orientation;
    private boolean isForceDualPage;

    public QuranPageAdapter(FragmentManager fm, Lifecycle lifecycle, Context context, int orientation) {
        super(fm, lifecycle);
        mushafVersion = QuranSettings.getInstance().getMushafVersion(context);
        isForceDualPage = QuranSettings.getInstance().getIsForceDualPage(context);
        this.orientation = orientation;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        Bundle bundle;
        int dualPagerPosition;

        if (QuranActivity.isLandscape(orientation) || isForceDualPage) {
            position++;
            dualPagerPosition = position - 1;
            fragment = new QuranDualPageFragment();
            bundle = new Bundle();
            bundle.putInt("dual_pager_position", dualPagerPosition);
            fragment.setArguments(bundle);
        } else {
            position++;
            dualPagerPosition = mushafVersion == QuranSettings.MADANI15LINE ? position : position + 1;
            fragment = new QuranPageFragment();
            bundle = new Bundle();
            bundle.putInt("page_number", dualPagerPosition);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        if (!QuranActivity.isLandscape(orientation) && !isForceDualPage) {
            if (mushafVersion == QuranSettings.MADANI15LINE)
                return 604;
            else
                return 847;
        } else {
            if (mushafVersion == QuranSettings.MADANI15LINE)
                return QuranConstants.madani15LineDualPageSets.length;
            else
                return QuranConstants.naskh13LineDualPageSets.length;
        }
    }
}
