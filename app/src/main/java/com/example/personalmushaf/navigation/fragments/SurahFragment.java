package com.example.personalmushaf.navigation.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.QuranPageData;
import com.example.personalmushaf.navigation.adapters.SurahAdapter;


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
        int type = getArguments().getInt("type");
        int juzNumber = getArguments().getInt("juz number");
        String[] dataSet;

        if (type == 0) {
            dataSet = QuranPageData.getInstance().surahNames;
        } else {
            dataSet = QuranPageData.getInstance().getSurahInJuzTitles[juzNumber-1];
        }

        v = inflater.inflate(R.layout.fragment_surah, container, false);
        surahRecyclerView = (RecyclerView) v.findViewById(R.id.surah_recycler_view);
        surahRecyclerView.setHasFixedSize(true);
        LinearLayoutManager surahLayoutManager = new LinearLayoutManager(getContext());
        adapter = new SurahAdapter(dataSet, type, juzNumber);
        surahRecyclerView.setAdapter(adapter);
        surahRecyclerView.setLayoutManager(surahLayoutManager);

        return v;
    }

}
