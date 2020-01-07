package com.android.personalmushaf.mushafinterfaces.strategies.concretenavigationstrategies;

import android.view.View;

import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzStrategy;
import com.android.personalmushaf.navigation.tabs.juztab.JuzAdapter;

public class ConcreteJuzStrategy implements JuzStrategy {
    private MushafMetadata mushafMetadata;

    public ConcreteJuzStrategy(MushafMetadata mushafMetadata) {
        this.mushafMetadata = mushafMetadata;
    }

    public JuzAdapter getJuzAdapter(View v) {
        return new JuzAdapter(v.getResources().getStringArray(R.array.juz_names),
                mushafMetadata.getNavigationData().getJuzPageNumbers(),
                mushafMetadata.getNavigationData().getJuzLengths());
    }
}
