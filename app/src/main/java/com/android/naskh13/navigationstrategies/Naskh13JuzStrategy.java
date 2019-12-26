package com.android.naskh13.navigationstrategies;

import android.view.View;

import com.android.naskh13.Naskh13NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.navigation.tabs.juztab.JuzAdapter;

public class Naskh13JuzStrategy implements JuzStrategy {
    public JuzAdapter getJuzAdapter(View v) {
        return new JuzAdapter(v.getResources().getStringArray(R.array.juz_names),
                Naskh13NavigationData.naskh13JuzPageNumbers,
                Naskh13NavigationData.naskh13JuzLengths);
    }
}
