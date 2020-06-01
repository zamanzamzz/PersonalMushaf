package com.ammanz.personalmushaf.quranpage;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ammanz.personalmushaf.QuranActivity;
import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.model.HighlightType;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;

public class QuranPageAdapter extends FragmentStatePagerAdapter {

    private SparseArray<QuranPage> registeredFragments = new SparseArray<>();
    private MushafMetadata mushafMetadata;
    private int numOfSinglePages;
    private int numOfDualPages;

    private int orientation;
    private boolean isForceDualPage;
    private QuranActivity quranActivity;

    public QuranPageAdapter(FragmentManager fm, QuranActivity context, int orientation) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mushafMetadata = QuranSettings.getInstance().getMushafMetadata(context);
        isForceDualPage = QuranSettings.getInstance().getIsForceDualPage(context);
        this.orientation = orientation;
        this.quranActivity = context;

        numOfSinglePages = mushafMetadata.getMaxPage() - mushafMetadata.getMinPage() + 1;
        numOfDualPages = numOfSinglePages % 2 == 0 ? numOfSinglePages / 2 : numOfSinglePages / 2 + 1;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        QuranPage fragment;

        if (QuranActivity.isLandscape(orientation) || isForceDualPage) {
            fragment = QuranDualPageFragment.newInstance(position, quranActivity.highlightedSurah, quranActivity.highlightedAyah);
            fragment.addObserver(quranActivity);
        } else {
            int pageNumber = getPageNumberFromPagerPosition(position);
            fragment = QuranPageFragment.newInstance(pageNumber, position, quranActivity.highlightedSurah, quranActivity.highlightedAyah);
            fragment.addObserver(quranActivity);
        }


        return fragment;
    }

    @Override
    public int getCount() {
        if (!QuranActivity.isLandscape(orientation) && !isForceDualPage) {
            return numOfSinglePages;
        } else {
            return numOfDualPages;
        }
    }

    public void highlightVisiblePages(int position, int selectedSurah, int selectedAyah) {
        getRegisteredFragment(position).highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        if (position > 0)
            getRegisteredFragment(position - 1).highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        if (position < getCount() - 1)
            getRegisteredFragment(position + 1).highlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
    }

    public void unhighlightVisiblePages(int position, int selectedSurah, int selectedAyah) {
        getRegisteredFragment(position).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        if (position > 0)
            getRegisteredFragment(position - 1).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
        if (position < getCount() - 1)
            getRegisteredFragment(position + 1).unhighlightAyah(selectedSurah, selectedAyah, HighlightType.SELECTION);
    }

    public void highlightGlyph(int position, int glyphIndex) {
        getRegisteredFragment(position).highlightGlyph(glyphIndex);
    }

    public int getNumOfGlyphs(int position) {
        return getRegisteredFragment(position).getNumOfGlyphs();
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

    private int getPageNumberFromPagerPosition(int position) {
        return position + mushafMetadata.getMinPage();
    }
}
