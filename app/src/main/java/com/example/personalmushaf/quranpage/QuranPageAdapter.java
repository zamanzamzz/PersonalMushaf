package com.example.personalmushaf.quranpage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.personalmushaf.QuranSettings;
import com.example.personalmushaf.navigation.QuranConstants;

import java.util.ArrayList;
import java.util.List;

public class QuranPageAdapter extends FragmentStateAdapter {

    private String mushafVersion;
    private String orientation;
    private boolean isForceDualPage;
    private List<Fragment> mFragmentList;
    private FragmentManager fm;

    public QuranPageAdapter(FragmentManager fm, Lifecycle lifecycle, Context context, String orientation) {
        super(fm, lifecycle);
        this.fm = fm;
        mushafVersion = QuranSettings.getInstance().getMushafVersion(context);
        isForceDualPage = QuranSettings.getInstance().getIsForceDualPage(context);
        this.orientation = orientation;
        mFragmentList = new ArrayList<>();

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        Bundle bundle;
        int dualPagerPosition;

        if (orientation.equals("landscape") || isForceDualPage) {
            position++;
            dualPagerPosition = position - 1;
            fragment = new QuranDualPageFragment();
            bundle = new Bundle();
            bundle.putInt("dual_pager_position", dualPagerPosition);
            fragment.setArguments(bundle);
        } else {
            position++;
            dualPagerPosition = mushafVersion.equals("madani_15_line") ? position : position + 1;
            fragment = new QuranPageFragment();
            bundle = new Bundle();
            bundle.putInt("page_number", dualPagerPosition);
            fragment.setArguments(bundle);
        }

        mFragmentList.add(fragment);

        return fragment;
    }

    @Override
    public int getItemCount() {
        if (orientation.equals("portrait") && !isForceDualPage) {
            if (mushafVersion.equals("madani_15_line"))
                return 604;
            else
                return 847;
        } else {
            if (mushafVersion.equals("madani_15_line"))
                return QuranConstants.madani15LineDualPageSets.length;
            else
                return QuranConstants.naskh13LineDualPageSets.length;
        }
    }



    public void removeAllfragments() {
        if ( mFragmentList != null ) {
            for ( Fragment fragment : mFragmentList ) {
                fm.beginTransaction().remove(fragment).commit();
            }
            mFragmentList.clear();
            notifyDataSetChanged();
        }
    }

    public void removeAllButLast() {
        if ( mFragmentList != null ) {
            for ( int i = 0; i < mFragmentList.size() - 3; i++) {
                fm.beginTransaction().remove(mFragmentList.get(i)).commit();
            }
            mFragmentList.clear();
            notifyDataSetChanged();
        }
    }

}
