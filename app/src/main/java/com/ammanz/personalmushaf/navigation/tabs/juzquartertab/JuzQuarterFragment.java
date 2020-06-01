package com.ammanz.personalmushaf.navigation.tabs.juzquartertab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.navigation.QuranConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzQuarterFragment extends Fragment {

    public JuzQuarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        int juzNumber = getArguments().getInt("juz number");
        MushafMetadata mushafMetadata = QuranSettings.getInstance().getMushafMetadata(getContext());


        RecyclerView juzQuarterRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzQuarterRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        JuzQuarterAdapter adapter = getJuzQuarterAdapter(juzNumber, mushafMetadata);
        juzQuarterRecyclerView.setAdapter(adapter);
        juzQuarterRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

    private JuzQuarterAdapter getJuzQuarterAdapter(int juzNumber, MushafMetadata mushafMetadata) {
        int MADANINUMOFQUARTERS = 8;
        int RUKUNUMOFQUARTERS = 4;

        int landmarkSystem = QuranSettings.getInstance().getLandMarkSystem(getContext());
        int[][] info;
        int[] pageNumbers;
        String[] prefixes;
        double[] lengths;

        if (mushafMetadata.getShouldDoRuku(landmarkSystem)) {
            info = getQuarterInfo(juzNumber, QuranConstants.quarterInfo, RUKUNUMOFQUARTERS);
            pageNumbers = getQuarterPageNumbers(juzNumber, mushafMetadata.getNavigationData().getQuarterPageNumbers(), RUKUNUMOFQUARTERS);
            prefixes = getQuarterStringResource(juzNumber, getResources().getStringArray(R.array.quarter_prefixes), RUKUNUMOFQUARTERS);
            lengths = getQuarterLengths(juzNumber, mushafMetadata.getNavigationData().getQuarterLengths(), RUKUNUMOFQUARTERS);
        } else  {

            info = getQuarterInfo(juzNumber, QuranConstants.hizbInfo, MADANINUMOFQUARTERS);
            pageNumbers = getQuarterPageNumbers(juzNumber, mushafMetadata.getNavigationData().getHizbPageNumbers(), MADANINUMOFQUARTERS);
            prefixes = getQuarterStringResource(juzNumber, getResources().getStringArray(R.array.hizb_prefixes), MADANINUMOFQUARTERS);
            lengths = getQuarterLengths(juzNumber, mushafMetadata.getNavigationData().getHizbLengths(), MADANINUMOFQUARTERS);
        }

        return new JuzQuarterAdapter(info, pageNumbers, prefixes, lengths);
    }

    

    private int[][] getQuarterInfo(int juzNumber, int[][] source, int numOfQuarters) {
        int[][] quarterInfo = new int[numOfQuarters][3];
        System.arraycopy(source, (juzNumber - 1) * numOfQuarters, quarterInfo, 0, numOfQuarters);
        return quarterInfo;
    }

    private int[] getQuarterPageNumbers(int juzNumber, int[] source, int numOfQuarters) {
        int[] quarterPageNumbers = new int[numOfQuarters];
        System.arraycopy(source, (juzNumber - 1) * numOfQuarters, quarterPageNumbers, 0, numOfQuarters);
        return quarterPageNumbers;
    }

    private double[] getQuarterLengths(int juzNumber, double[] source, int numOfQuarters) {
        double[] quarterLengths = new double[numOfQuarters];
        System.arraycopy(source, (juzNumber - 1) * numOfQuarters, quarterLengths, 0, numOfQuarters);
        return quarterLengths;
    }

    private String[] getQuarterStringResource(int juzNumber, String[] source, int numOfQuarters) {
        String[] quarterPrefixes = new String[numOfQuarters];
        System.arraycopy(source, (juzNumber - 1)*numOfQuarters, quarterPrefixes, 0, numOfQuarters);
        return quarterPrefixes;
    }

}
