package com.example.personalmushaf.navigation.tabs.juzquartertab;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.ThirteenLinePageData;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzQuarterFragment extends Fragment {

    private View v;
    private RecyclerView juzRecyclerView;
    private JuzQuarterAdapter adapter;
    private String[][] dataSet;
    private ActionBar actionBar;
    private int juzNumber;

    public JuzQuarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab, container, false);

        juzNumber = getArguments().getInt("juz number");

        dataSet = ThirteenLinePageData.getInstance().juzQuarterInfo[juzNumber-1];

        juzRecyclerView = (RecyclerView) v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        adapter = new JuzQuarterAdapter(dataSet);

        juzRecyclerView.setAdapter(adapter);
        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}