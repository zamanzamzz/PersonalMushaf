package com.android.naskh15.navigationstrategies;

import android.content.Context;
import android.view.View;

import com.android.naskh15.Naskh15NavigationData;
import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies.AbstractJuzQuarterStrategy;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.JuzQuarterStrategy;
import com.android.personalmushaf.navigation.QuranConstants;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterAdapter;

public class Naskh15JuzQuarterStrategy extends AbstractJuzQuarterStrategy implements JuzQuarterStrategy {
    private static int RUKUNUMOFQUARTERS = 4;
    private static int MADANINUMOFQUARTERS = 8;

    public JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, View v, Context context) {
        int landmarkSystem = QuranSettings.getInstance().getLandMarkSystem(context);
        boolean shouldDoRuku = landmarkSystem == QuranSettings.DEFAULT_LANDMARK_SYSTEM || landmarkSystem == QuranSettings.RUKU;
        int[][] quarterInfo = shouldDoRuku ? getQuarterInfo(juzNumber, QuranConstants.naskhQuarterInfo, RUKUNUMOFQUARTERS) :
                                            getQuarterInfo(juzNumber, QuranConstants.madaniHizbInfo, MADANINUMOFQUARTERS);
        int[] pageNumbers   = shouldDoRuku ? getQuarterPageNumbers(juzNumber, Naskh15NavigationData.naskh15QuarterPageNumbers, RUKUNUMOFQUARTERS) :
                                            getQuarterPageNumbers(juzNumber, Naskh15NavigationData.naskh15HizbPageNumbers, MADANINUMOFQUARTERS);
        String[] prefixes   = shouldDoRuku ? getQuarterStringResource(juzNumber, v.getResources().getStringArray(R.array.naskh_quarter_prefixes), RUKUNUMOFQUARTERS) :
                                                getQuarterStringResource(juzNumber, v.getResources().getStringArray(R.array.madani_quarter_prefixes), MADANINUMOFQUARTERS);
        double[] lengths    = shouldDoRuku ? getQuarterLengths(juzNumber, Naskh15NavigationData.naskh15QuarterLengths, RUKUNUMOFQUARTERS) :
                                            getQuarterLengths(juzNumber, Naskh15NavigationData.naskh15HizbLengths, MADANINUMOFQUARTERS);
        return new JuzQuarterAdapter(quarterInfo, pageNumbers, prefixes, lengths);
    }
}
