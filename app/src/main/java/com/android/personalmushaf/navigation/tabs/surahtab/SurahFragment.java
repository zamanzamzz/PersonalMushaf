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
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.SurahStrategy;


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
        SurahStrategy surahStrategy = QuranSettings.getInstance().getMushafStrategy(getContext()).getSurahStrategy();

        View v = inflater.inflate(R.layout.fragment_tab, container, false);
        RecyclerView surahRecyclerView = v.findViewById(R.id.tab_recycler_view);
        surahRecyclerView.setHasFixedSize(true);
        LinearLayoutManager surahLayoutManager = new LinearLayoutManager(getContext());

        SurahAdapter adapter = surahStrategy.getSurahAdapter(juzNumber, v);
        surahRecyclerView.setAdapter(adapter);
        surahRecyclerView.setLayoutManager(surahLayoutManager);

        return v;
    }
}
