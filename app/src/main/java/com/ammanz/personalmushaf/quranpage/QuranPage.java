package com.ammanz.personalmushaf.quranpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.QuranActivity;
import com.ammanz.personalmushaf.model.Ayah;
import com.ammanz.personalmushaf.model.HighlightType;
import com.ammanz.personalmushaf.model.PageData;
import com.ammanz.personalmushaf.util.FileUtils;

public abstract class QuranPage extends Fragment {
    private FragmentObserver mObservers = new FragmentObserver();

    public void addObserver(QuranActivity quranActivity) {
        mObservers.addObserver(quranActivity);
    }

    protected void updateAdapter(Ayah ayah, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("ayah_key", ayah.getKey());
        bundle.putInt("position", position);
        mObservers.setChanged();
        mObservers.notifyObservers(bundle);
    }

    public abstract void highlightAyah(int sura, int ayah, HighlightType highlightType);

    public abstract void unhighlightAyah(int sura, int ayah, HighlightType highlightType);

    protected String getPagePath(int pageNumber, MushafMetadata mushafMetadata) {
        return FileUtils.ASSETSDIRECTORY + "/" + mushafMetadata.getDirectoryName() + "/images/pg_" + pageNumber + ".png";
    }

    protected PageData getPageData(int pageNumber, MushafMetadata mushafMetadata) {
        return new PageData(pageNumber, mushafMetadata.getDatabasePath());
    }

    public abstract void highlightGlyph(int glyphIndex);

    public abstract int getNumOfGlyphs();

}
