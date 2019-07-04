package com.example.personalmushaf.navigation.tabs.surahtab;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.NavigationDataUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurahFragment extends Fragment {

    private View v;
    private RecyclerView surahRecyclerView;
    private SurahAdapter adapter;


    public SurahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int juzNumber = getArguments().getInt("juz number");
        String[][] dataSet;

        String[] colName = {"Madani15LinePageNumber", "\"13LinePageNumber\"", "IsMakki", "Name"};

        dataSet = NavigationDataUtil.fetchNavigationData(colName, "Surah", juzNumber, false);

        v = inflater.inflate(R.layout.fragment_tab, container, false);
        surahRecyclerView = v.findViewById(R.id.tab_recycler_view);
        surahRecyclerView.setHasFixedSize(true);
        LinearLayoutManager surahLayoutManager = new LinearLayoutManager(getContext());
        adapter = new SurahAdapter(dataSet, juzNumber);
        surahRecyclerView.setAdapter(adapter);
        surahRecyclerView.setLayoutManager(surahLayoutManager);

        return v;
    }

}
