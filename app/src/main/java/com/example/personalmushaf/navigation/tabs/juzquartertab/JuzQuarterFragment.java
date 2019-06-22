package com.example.personalmushaf.navigation.tabs.juzquartertab;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.MadaniFifteenLinePageData;
import com.example.personalmushaf.navigation.NaskhThirteenLinePageData;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzQuarterFragment extends Fragment {

    private View v;
    private RecyclerView juzQuarterRecyclerView;
    private JuzQuarterAdapter adapter;
    private String[][] dataSet;
    private int juzNumber;
    private SharedPreferences preferences;

    public JuzQuarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab, container, false);

        juzNumber = getArguments().getInt("juz number");

        preferences = PreferenceManager.getDefaultSharedPreferences(container.getContext());

        String mushaf = preferences.getString("mushaf", "madani_15_line");
        if (mushaf.equals("madani_15_line"))
            dataSet = MadaniFifteenLinePageData.juzQuarterInfo[juzNumber-1];
        else
            dataSet = NaskhThirteenLinePageData.juzQuarterInfo[juzNumber-1];

        juzQuarterRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzQuarterRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        adapter = new JuzQuarterAdapter(dataSet, juzNumber);

        juzQuarterRecyclerView.setAdapter(adapter);
        juzQuarterRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
