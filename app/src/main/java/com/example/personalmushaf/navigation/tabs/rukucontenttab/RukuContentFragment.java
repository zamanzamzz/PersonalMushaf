package com.example.personalmushaf.navigation.tabs.rukucontenttab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.NavigationDataUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class RukuContentFragment extends Fragment {

    private View v;
    private RecyclerView juzRecyclerView;
    private RukuContentAdapter adapter;
    private String[][] dataSet;
    private int juzNumber;

    public RukuContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab, container, false);

        juzNumber = getArguments().getInt("juz number");

        juzRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);

        String[] colNames = {"PageNumber", "Surah", "Ayah", "Prefix"};

        dataSet = NavigationDataUtil.fetchNavigationData(colNames, "Ruku", juzNumber, false);

        adapter = new RukuContentAdapter(dataSet);
        juzRecyclerView.setAdapter(adapter);

        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());

        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
