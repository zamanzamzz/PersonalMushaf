package com.example.personalmushaf.navigation.tabs.juztab;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.NavigationDataUtil;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzFragment extends Fragment {

    private View v;
    private RecyclerView juzRecyclerView;
    private JuzAdapter adapter;
    private String[][] dataSet;
    private ActionBar actionBar;
    private SharedPreferences preferences;


    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab, container, false);

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        preferences = PreferenceManager.getDefaultSharedPreferences(container.getContext());

        String mushaf = preferences.getString("mushaf", "madani_15_line");

        actionBar.setTitle("Qur'an Contents");

        String[] colNames = {"", "", "Juz", "Prefix"};
        if (mushaf.equals("madani_15_line")) {
            colNames[0] = "Madani15LinePageNumber";
            colNames[1] = "Madani15LineLength";
        } else {
            colNames[0] = "\"13LinePageNumber\"";
            colNames[1] = "\"13LineLength\"b";
        }

        dataSet = NavigationDataUtil.fetchNavigationData(colNames, "Juz", -1, false);


        juzRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        adapter = new JuzAdapter(dataSet);

        juzRecyclerView.setAdapter(adapter);
        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
