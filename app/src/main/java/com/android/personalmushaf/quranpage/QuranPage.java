package com.android.personalmushaf.quranpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.android.personalmushaf.QuranActivity;
import com.android.personalmushaf.model.Ayah;
import com.android.personalmushaf.model.HighlightType;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.FileUtils;

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

}
