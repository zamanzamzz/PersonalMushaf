package com.android.naskh15.navigationstrategies;

import android.view.View;

import com.android.naskh15.Naskh15NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.navigation.tabs.juztab.JuzAdapter;

public class Naskh15JuzStrategy implements JuzStrategy {
    public JuzAdapter getJuzAdapter(View v) {
        return new JuzAdapter(v.getResources().getStringArray(R.array.juz_names),
                Naskh15NavigationData.naskh15JuzPageNumbers,
                Naskh15NavigationData.naskh15JuzLengths);
    }
}
