package com.example.personalmushaf.navigation.tabs.surahtab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personalmushaf.QuranSettings;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.NavigationDataUtil;
import com.example.personalmushaf.navigation.navigationdata.QuranConstants;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurahFragment extends Fragment {

    private View v;
    private RecyclerView surahRecyclerView;
    private SurahAdapter adapter;
    private int mushafVersion;
    private String[] prefixes;


    public SurahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int juzNumber = getArguments().getInt("juz number");
        int[][] dataset = QuranConstants.getSurahsInJuz(juzNumber);

        v = inflater.inflate(R.layout.fragment_tab, container, false);
        surahRecyclerView = v.findViewById(R.id.tab_recycler_view);
        surahRecyclerView.setHasFixedSize(true);
        LinearLayoutManager surahLayoutManager = new LinearLayoutManager(getContext());

        mushafVersion = QuranSettings.getInstance().getMushafVersion(getContext());
        prefixes = new String[dataset.length];

        if (dataset.length > 0)
            System.arraycopy(getResources().getStringArray(R.array.surah_names), dataset[0][0] - 1, prefixes, 0, dataset.length);

        adapter = new SurahAdapter(dataset, prefixes, mushafVersion);
        surahRecyclerView.setAdapter(adapter);
        surahRecyclerView.setLayoutManager(surahLayoutManager);

        return v;
    }

}
