package com.example.personalmushaf.navigation.tabs.surahtab;


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
public class SurahFragment extends Fragment {

    private View v;
    private RecyclerView surahRecyclerView;
    private SurahAdapter adapter;
    private SharedPreferences preferences;


    public SurahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int juzNumber = getArguments().getInt("juz number");
        String[][] dataSet;

        preferences = PreferenceManager.getDefaultSharedPreferences(container.getContext());
        String mushaf = preferences.getString("mushaf", "madani_15_line");

        if (mushaf.equals("madani_15_line")) {
            if (juzNumber < 0) {
                dataSet = MadaniFifteenLinePageData.surahInfo;
            } else {
                dataSet = MadaniFifteenLinePageData.surahInJuzInfo[juzNumber-1];
            }
        }
        else {
            if (juzNumber < 0) {
                dataSet = NaskhThirteenLinePageData.surahInfo;
            } else {
                dataSet = NaskhThirteenLinePageData.surahInJuzInfo[juzNumber-1];
            }
        }



        v = inflater.inflate(R.layout.fragment_tab, container, false);
        surahRecyclerView = (RecyclerView) v.findViewById(R.id.tab_recycler_view);
        surahRecyclerView.setHasFixedSize(true);
        LinearLayoutManager surahLayoutManager = new LinearLayoutManager(getContext());
        adapter = new SurahAdapter(dataSet, juzNumber);
        surahRecyclerView.setAdapter(adapter);
        surahRecyclerView.setLayoutManager(surahLayoutManager);

        return v;
    }

}
