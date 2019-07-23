package com.example.personalmushaf.quranpage;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.personalmushaf.navigation.QuranConstants;

public class QuranPageAdapter extends FragmentPagerAdapter {

    private String mushafVersion;
    private String orientation;
    private boolean isForceDualPage;

    public QuranPageAdapter(FragmentManager fm, Context context, String orientation) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mushafVersion = PreferenceManager.getDefaultSharedPreferences(context).getString("mushaf", "madani_15_line");
        isForceDualPage = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("force_dual_page", false);
        this.orientation = orientation;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        Bundle bundle;
        int pageNumber;

        if (orientation.equals("landscape") || isForceDualPage) {
            position++;
            pageNumber = getCount() - position;
            fragment = new QuranDualPageFragment();
            bundle = new Bundle();
            bundle.putInt("page_number", pageNumber);
            fragment.setArguments(bundle);
        } else {
            position++;
            pageNumber = getCount() + 1 - position;
            fragment = new QuranPageFragment();
            bundle = new Bundle();
            bundle.putInt("page_number", pageNumber);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        if (orientation.equals("portrait") && !isForceDualPage) {
            if (mushafVersion.equals("madani_15_line"))
                return 604;
            else
                return 848;
        } else {
            if (mushafVersion.equals("madani_15_line"))
                return QuranConstants.madani15LineDualPageSets.length;
            else
                return QuranConstants.naskh13LineDualPageSets.length;
        }
    }

}
