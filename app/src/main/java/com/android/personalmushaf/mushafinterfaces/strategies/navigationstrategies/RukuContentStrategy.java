package com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies;

import android.view.View;

import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentAdapter;

public interface RukuContentStrategy {
    RukuContentAdapter getRukuContentAdapter(int juzNumber, View v);
}
