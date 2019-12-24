package com.android.madani15.navigationstrategies;

import android.view.View;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.navigation.tabs.juztab.JuzAdapter;

public class Madani15JuzStrategy implements JuzStrategy {
    public JuzAdapter getJuzAdapter(View v) {
        return new JuzAdapter(v.getResources().getStringArray(R.array.juz_names), Madani15NavigationData.juzInfo);
    }
}
