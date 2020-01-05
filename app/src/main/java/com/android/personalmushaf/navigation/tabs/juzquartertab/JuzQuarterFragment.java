package com.android.personalmushaf.navigation.tabs.juzquartertab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzQuarterFragment extends Fragment {

    public JuzQuarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        int juzNumber = getArguments().getInt("juz number");


        RecyclerView juzQuarterRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzQuarterRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        JuzQuarterAdapter adapter = QuranSettings.getInstance().getMushafStrategy(getContext()).getJuzQuarterStrategy().getJuzQuarterAdapter(juzNumber, v, getContext());
        juzQuarterRecyclerView.setAdapter(adapter);
        juzQuarterRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
