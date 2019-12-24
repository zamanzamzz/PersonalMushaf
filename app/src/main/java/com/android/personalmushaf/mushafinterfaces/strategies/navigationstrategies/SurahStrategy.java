package com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies;

import android.view.View;

import com.android.personalmushaf.navigation.tabs.surahtab.SurahAdapter;

public interface SurahStrategy {
    SurahAdapter getSurahAdapter(int juzNumber, View v);
}
