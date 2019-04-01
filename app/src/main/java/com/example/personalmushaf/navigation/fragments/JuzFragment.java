package com.example.personalmushaf.navigation.fragments;


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
import com.example.personalmushaf.navigation.adapters.JuzAdapter;
import com.example.personalmushaf.navigation.QuranPageData;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzFragment extends Fragment {

    private View v;
    private RecyclerView juzRecyclerView;
    private JuzAdapter adapter;
    private int type;
    private int juzNumber;
    private String[] dataSet;
    private ActionBar actionBar;

    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_juz, container, false);

        type = getArguments().getInt("type");
        juzNumber = getArguments().getInt("juz number");
        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if (type == 0) {
            dataSet = QuranPageData.getInstance().juzTitles;
            actionBar.setTitle("Qur'an Contents");

        } else if (type == 1) {
            dataSet = QuranPageData.getInstance().juzContentTitles[juzNumber-1];
            actionBar.setTitle("Chapter " + Integer.toString(juzNumber));

        } else {
            dataSet = (QuranPageData.getInstance().rukuContentTitles)[juzNumber-1];
            actionBar.setTitle("Chapter " + Integer.toString(juzNumber));
        }

        juzRecyclerView = (RecyclerView) v.findViewById(R.id.juz_recycler_view);
        juzRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        adapter = new JuzAdapter(dataSet, type, juzNumber);

        juzRecyclerView.setAdapter(adapter);
        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
