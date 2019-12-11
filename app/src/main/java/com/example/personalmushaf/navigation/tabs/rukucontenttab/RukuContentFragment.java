package com.example.personalmushaf.navigation.tabs.rukucontenttab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.navigationdata.QuranConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class RukuContentFragment extends Fragment {

    private View v;
    private RecyclerView juzRecyclerView;
    private RukuContentAdapter adapter;
    private int[][] dataset;
    private String[] prefixes;
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

        dataset = QuranConstants.rukuInfo[juzNumber-1];
        prefixes = v.getResources().getStringArray(v.getResources().getIdentifier("juz_" + (juzNumber - 1), "array", v.getContext().getPackageName()));

        adapter = new RukuContentAdapter(dataset, prefixes);
        juzRecyclerView.setAdapter(adapter);

        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());

        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
