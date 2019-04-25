package com.example.personalmushaf.navigation.tabs.juztab;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
public class JuzFragment extends Fragment {

    private View v;
    private RecyclerView juzRecyclerView;
    private JuzAdapter adapter;
    private String[][] dataSet;
    private ActionBar actionBar;

    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab, container, false);

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        dataSet = ThirteenLinePageData.juzInfo;
        actionBar.setTitle("Qur'an Contents");


        juzRecyclerView = (RecyclerView) v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        adapter = new JuzAdapter(dataSet);

        juzRecyclerView.setAdapter(adapter);
        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
