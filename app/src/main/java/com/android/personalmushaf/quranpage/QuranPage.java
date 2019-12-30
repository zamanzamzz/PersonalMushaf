package com.android.personalmushaf.quranpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.personalmushaf.model.Ayah;
import com.android.personalmushaf.model.HighlightType;

import java.util.Observer;

public abstract class QuranPage extends Fragment {
    private FragmentObserver mObservers = new FragmentObserver();

    public void addObserver(FragmentStatePagerAdapter adapter) {
        mObservers.addObserver((Observer) (adapter));
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
}
