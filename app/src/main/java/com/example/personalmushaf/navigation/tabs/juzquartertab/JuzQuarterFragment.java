package com.example.personalmushaf.navigation.tabs.juzquartertab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalmushaf.QuranSettings;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.navigationdata.QuranConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzQuarterFragment extends Fragment {

    private View v;
    private RecyclerView juzQuarterRecyclerView;
    private JuzQuarterAdapter adapter;
    private int[][] dataset;
    private String[] prefixes;
    private String[] lengths;
    private int juzIndex;

    public JuzQuarterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab, container, false);

        juzIndex = getArguments().getInt("juz number") - 1;

        int mushafVersion = QuranSettings.getInstance().getMushafVersion(v.getContext());

        if (mushafVersion == QuranSettings.MADANI15LINE) {
            dataset = new int[8][4];
            prefixes = new String[8];
            System.arraycopy(QuranConstants.madaniQuarterInfo, juzIndex *8, dataset, 0, 8);
            System.arraycopy(v.getResources().getStringArray(R.array.madani_quarter_prefixes), juzIndex*8, prefixes, 0, 8);
        } else {
            dataset = new int[4][4];
            prefixes = new String[4];
            lengths = new String[4];
            System.arraycopy(QuranConstants.naskhQuarterInfo, juzIndex *4, dataset, 0, 4);
            System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_prefixes), juzIndex*4, prefixes, 0, 4);
            System.arraycopy(v.getResources().getStringArray(R.array.naskh_quarter_lengths), juzIndex*4, lengths, 0, 4);
        }


        juzQuarterRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzQuarterRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        adapter = new JuzQuarterAdapter(dataset, prefixes, lengths);
        juzQuarterRecyclerView.setAdapter(adapter);
        juzQuarterRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
