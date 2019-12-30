package com.android.personalmushaf.quranpage;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.personalmushaf.QuranActivity;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.model.HighlightType;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageAdapterStrategy;

import java.util.Observable;
import java.util.Observer;

public class QuranPageAdapter extends FragmentStatePagerAdapter implements Observer {

    private SparseArray<QuranPage> registeredFragments = new SparseArray<>();
    private QuranPageAdapterStrategy quranPageAdapterStrategy;
    private int orientation;
    private boolean isForceDualPage;
    protected Integer highlightedSurah = null;
    protected Integer highlightedAyah = null;
    protected boolean isHighlighted = false;

    public QuranPageAdapter(FragmentManager fm, QuranPageAdapterStrategy quranPageAdapterStrategy,
                            Context context, int orientation) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.quranPageAdapterStrategy = quranPageAdapterStrategy;
        isForceDualPage = QuranSettings.getInstance().getIsForceDualPage(context);
        this.orientation = orientation;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        QuranPage fragment;

        if (QuranActivity.isLandscape(orientation) || isForceDualPage) {
            fragment = QuranDualPageFragment.newInstance(position, highlightedSurah, highlightedAyah);
            fragment.addObserver(this);
        } else {
            int pageNumber = quranPageAdapterStrategy.getPageNumberFromPagerPosition(position);
            fragment = QuranPageFragment.newInstance(pageNumber, position, highlightedSurah, highlightedAyah);
            fragment.addObserver(this);
        }


        return fragment;
    }

    @Override
    public int getCount() {
        if (!QuranActivity.isLandscape(orientation) && !isForceDualPage) {
            return quranPageAdapterStrategy.getNumOfSinglePages();
        } else {
            return quranPageAdapterStrategy.getNumOfDualPages();
        }
    }

    public void update(Observable o, Object arg) {
        Bundle arguments = (Bundle) arg;
        String[] ayahKey = arguments.getString("ayah_key").split(":");
        int position = arguments.getInt("position");
        Integer selectedSurah = Integer.parseInt(ayahKey[0]);
        Integer selectedAyah = Integer.parseInt(ayahKey[1]);
        if (!isHighlighted) {
            highlightVisiblePages(position, selectedSurah, selectedAyah);
            highlightedSurah = selectedSurah;
            highlightedAyah = selectedAyah;
            isHighlighted = true;
        } else if (selectedSurah.equals(highlightedSurah) && selectedAyah.equals(highlightedAyah)) {
            unhighlightVisiblePages(position, selectedSurah, selectedAyah);
            highlightedSurah = null;
            highlightedAyah = null;
            isHighlighted = false;
        } else {
            highlightVisiblePages(position, selectedSurah, selectedAyah);
            highlightedSurah = selectedSurah;
            highlightedAyah = selectedAyah;
            isHighlighted = true;
        }
    }

    private void highlightVisiblePages(int position, int selectedSurah, int selectedAyah) {
        getRegisteredFragment(position).highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        if (position > 0)
            getRegisteredFragment(position - 1).highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        if (position < getCount() - 1)
            getRegisteredFragment(position + 1).highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
    }

    private void unhighlightVisiblePages(int position, int selectedSurah, int selectedAyah) {
        getRegisteredFragment(position).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        if (position > 0)
            getRegisteredFragment(position - 1).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        if (position < getCount() - 1)
            getRegisteredFragment(position + 1).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
    }


    @NonNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        QuranPage fragment = (QuranPage) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public QuranPage getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
