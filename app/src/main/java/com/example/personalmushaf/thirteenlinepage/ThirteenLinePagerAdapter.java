package com.example.personalmushaf.thirteenlinepage;

import android.os.Bundle;
import android.view.View;

import com.example.personalmushaf.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ThirteenLinePagerAdapter extends FragmentPagerAdapter {

    private static int pageNumber = 849;

    public ThirteenLinePagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        ThirteenLinePageFragment thirteenLinePageFragment = new ThirteenLinePageFragment();
        position = position + 1;
        String pageNum;
        int currentPageNumber = pageNumber - position;

        pageNum = Integer.toString(currentPageNumber);
        Bundle bundle = new Bundle();
        bundle.putString("page_number", pageNum);
        thirteenLinePageFragment.setArguments(bundle);
        return thirteenLinePageFragment;
    }

    @Override
    public int getCount() {
        return 848;
    }
}
