package com.ammanz.personalmushaf.quranpage;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ammanz.personalmushaf.QuranActivity;
import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.model.HighlightType;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;

public class QuranPageAdapter extends FragmentStateAdapter {

    private SparseArray<QuranPage> registeredFragments = new SparseArray<>();
    private MushafMetadata mushafMetadata;
    private int numOfSinglePages;
    private int numOfDualPages;

    private QuranActivity quranActivity;
    private QuranSettings quranSettings;

    public QuranPageAdapter(FragmentManager fm, QuranActivity context, Lifecycle lifecycle) {
        super(fm, lifecycle);
        this.quranSettings = QuranSettings.getInstance();
        this.mushafMetadata = quranSettings.getMushafMetadata(context);
        this.quranActivity = context;
        registerFragmentTransactionCallback(new FragmentTransactionCallback() {
            @NonNull
            @Override
            public OnPostEventListener onFragmentPreAdded(@NonNull Fragment fragment) {
                QuranPage page = (QuranPage) fragment;
                registeredFragments.put(page.getPosition(), page);
                if (quranActivity.isHighlighted && shouldHighlightFragment(page.getPosition())) {
                    page.setHighlightedSurahAndAyah(quranActivity.highlightedSurah, quranActivity.highlightedAyah);
                }
                return super.onFragmentPreAdded(fragment);
            }

            @NonNull
            @Override
            public OnPostEventListener onFragmentPreRemoved(@NonNull Fragment fragment) {
                QuranPage page = (QuranPage) fragment;
                registeredFragments.remove(page.getPosition());
                return super.onFragmentPreRemoved(fragment);
            }
        });
        numOfSinglePages = mushafMetadata.getMaxPage() - mushafMetadata.getMinPage() + 1;
        numOfDualPages = numOfSinglePages % 2 == 0 ? numOfSinglePages / 2 : numOfSinglePages / 2 + 1;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        QuranPage fragment;

        if (quranActivity.currentShouldUseDualPages) {
            fragment = QuranDualPageFragment.newInstance(position);
            fragment.setPosition(position);
            fragment.addObserver(quranActivity);
        } else {
            int pageNumber = getPageNumberFromPagerPosition(position);
            fragment = QuranPageFragment.newInstance(pageNumber, position);
            fragment.setPosition(position);
            fragment.addObserver(quranActivity);
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        if (!quranActivity.currentShouldUseDualPages) {
            return numOfSinglePages;
        } else {
            return numOfDualPages;
        }
    }

    public boolean shouldHighlightFragment(int position) {
        if (position == quranActivity.highlightedPosition)
            return true;

        if (quranActivity.highlightedPosition > 0 && position == quranActivity.highlightedPosition - 1)
            return true;

        return quranActivity.highlightedPosition < getItemCount() - 1 && position == quranActivity.highlightedPosition + 1;
    }

    public void highlightVisiblePages(int position, int selectedSurah, int selectedAyah) {
        QuranPage registeredFragment = getRegisteredFragment(position);
        if (registeredFragment != null) {
            registeredFragment.highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
            if (position > 0)
                getRegisteredFragment(position - 1).highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
            if (position < getItemCount() - 1)
                getRegisteredFragment(position + 1).highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        }
    }

    public void unhighlightVisiblePages(int position, int selectedSurah, int selectedAyah) {
        QuranPage registeredFragment = getRegisteredFragment(position);
        if (registeredFragment != null) {
            getRegisteredFragment(position).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
            if (position > 0)
                getRegisteredFragment(position - 1).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
            if (position < getItemCount() - 1)
                getRegisteredFragment(position + 1).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        }
    }

    public void highlightGlyph(int position, int glyphIndex) {
        getRegisteredFragment(position).highlightGlyph(glyphIndex);
    }

    public int getNumOfGlyphs(int position) {
        return getRegisteredFragment(position).getNumOfGlyphs();
    }


    public QuranPage getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    private int getPageNumberFromPagerPosition(int position) {
        return position + mushafMetadata.getMinPage();
    }
}
