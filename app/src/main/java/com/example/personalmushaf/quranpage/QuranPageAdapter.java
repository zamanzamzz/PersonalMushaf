package com.example.personalmushaf.quranpage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.personalmushaf.QuranSettings;
import com.example.personalmushaf.navigation.QuranConstants;

public class QuranPageAdapter extends FragmentStateAdapter {

    private String mushafVersion;
    private String orientation;
    private boolean isForceDualPage;

    public QuranPageAdapter(FragmentManager fm, Lifecycle lifecycle, Context context, String orientation) {
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
        int pageNumber;

        if (orientation.equals("landscape") || isForceDualPage) {
            position++;
            pageNumber = position - 1;
            fragment = new QuranDualPageFragment();
            bundle = new Bundle();
            bundle.putInt("page_number", pageNumber);
            fragment.setArguments(bundle);
        } else {
            position++;
            pageNumber = position;
            fragment = new QuranPageFragment();
            bundle = new Bundle();
            bundle.putInt("page_number", pageNumber);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
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
