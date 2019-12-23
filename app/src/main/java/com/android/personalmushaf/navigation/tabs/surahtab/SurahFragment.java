package com.android.personalmushaf.navigation.tabs.surahtab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.NavigationStrategy;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurahFragment extends Fragment {

    public SurahFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int juzNumber = getArguments().getInt("juz number");
        NavigationStrategy navigationStrategy = QuranSettings.getInstance().getMushafStrategy(getContext()).getNavivationStrategy();

        int[][] dataset = navigationStrategy.getSurahsInJuz(juzNumber);

        View v = inflater.inflate(R.layout.fragment_tab, container, false);
        RecyclerView surahRecyclerView = v.findViewById(R.id.tab_recycler_view);
        surahRecyclerView.setHasFixedSize(true);
        LinearLayoutManager surahLayoutManager = new LinearLayoutManager(getContext());
        String[] prefixes = new String[dataset.length];

        if (dataset.length > 0)
            System.arraycopy(getResources().getStringArray(R.array.surah_names), dataset[0][0] - 1, prefixes, 0, dataset.length);

        SurahAdapter adapter = new SurahAdapter(dataset, prefixes, navigationStrategy);
        surahRecyclerView.setAdapter(adapter);
        surahRecyclerView.setLayoutManager(surahLayoutManager);

        return v;
    }

}
