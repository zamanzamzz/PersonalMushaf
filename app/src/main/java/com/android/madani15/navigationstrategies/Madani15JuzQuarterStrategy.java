package com.android.madani15.navigationstrategies;

import android.content.Context;
import android.view.View;

import com.android.madani15.Madani15NavigationData;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractJuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class Madani15JuzQuarterStrategy extends AbstractJuzQuarterStrategy implements JuzQuarterStrategy {
    private static int RUKUNUMOFQUARTERS = 4;
    private static int MADANINUMOFQUARTERS = 8;

    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v, Context context) {
        int landmarkSystem = QuranSettings.getInstance().getLandMarkSystem(context);
        boolean shouldDoHizb = landmarkSystem == QuranSettings.DEFAULT_LANDMARK_SYSTEM || landmarkSystem == QuranSettings.HIZB;
        int[][] info          = shouldDoHizb ? getQuarterInfo(juzNumber, QuranConstants.madaniHizbInfo, MADANINUMOFQUARTERS) :
                                            getQuarterInfo(juzNumber, QuranConstants.naskhQuarterInfo, RUKUNUMOFQUARTERS);
        int[] pageNumbers     = shouldDoHizb ? getQuarterPageNumbers(juzNumber, Madani15NavigationData.madani15HizbPageNumbers, MADANINUMOFQUARTERS) :
                                            getQuarterPageNumbers(juzNumber, Madani15NavigationData.madani15QuarterPageNumbers, RUKUNUMOFQUARTERS);
        String[] prefixes     = shouldDoHizb ? getQuarterStringResource(juzNumber, v.getResources().getStringArray(R.array.madani_quarter_prefixes), MADANINUMOFQUARTERS) :
                                            getQuarterStringResource(juzNumber, v.getResources().getStringArray(R.array.naskh_quarter_prefixes), RUKUNUMOFQUARTERS);
        double[] lengths      = shouldDoHizb ? getQuarterLengths(juzNumber, Madani15NavigationData.madani15HizbLengths, MADANINUMOFQUARTERS) :
                                            getQuarterLengths(juzNumber, Madani15NavigationData.madani15QuarterLengths, RUKUNUMOFQUARTERS);
        return new JuzQuarterAdapter(info, pageNumbers, prefixes, lengths);
    }
}
