package com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies;

import android.content.Context;
import android.view.View;

import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public interface JuzQuarterStrategy {
    JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v, Context context);
}
