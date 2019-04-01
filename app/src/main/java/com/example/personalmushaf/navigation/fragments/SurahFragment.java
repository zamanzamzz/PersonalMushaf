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

        v = inflater.inflate(R.layout.fragment_surah, container, false);
        surahRecyclerView = (RecyclerView) v.findViewById(R.id.surah_recycler_view);
        surahRecyclerView.setHasFixedSize(true);
        LinearLayoutManager surahLayoutManager = new LinearLayoutManager(getContext());
        adapter = new SurahAdapter(QuranPageData.getInstance().surahNames);
        surahRecyclerView.setAdapter(adapter);
        surahRecyclerView.setLayoutManager(surahLayoutManager);

        return v;
    }

}
