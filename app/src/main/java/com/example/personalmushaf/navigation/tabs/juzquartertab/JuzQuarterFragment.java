package com.example.personalmushaf.navigation.tabs.juzquartertab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalmushaf.QuranSettings;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.NavigationDataUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzQuarterFragment extends Fragment {

    private View v;
    private RecyclerView juzQuarterRecyclerView;
    private JuzQuarterAdapter adapter;
    private String[][] dataSet;
    private int juzNumber;

    public JuzQuarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab, container, false);

        juzNumber = getArguments().getInt("juz number");

        String[] colNames = {"PageNumber", "Surah", "Ayah", "Prefix"};
        String[] colNames13Line = {"PageNumber", "Surah", "Ayah", "Prefix", "Length"};


        String mushafVersion = QuranSettings.getInstance().getMushafVersion(v.getContext());

        dataSet = mushafVersion.equals("madani_15_line") ? NavigationDataUtil.fetchNavigationData(colNames, "MadaniQuarter", juzNumber, false) :
                                                    NavigationDataUtil.fetchNavigationData(colNames13Line, "NaskhQuarter", juzNumber, true);


        juzQuarterRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzQuarterRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        adapter = new JuzQuarterAdapter(dataSet);

        juzQuarterRecyclerView.setAdapter(adapter);
        juzQuarterRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
