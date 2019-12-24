package com.android.personalmushaf.navigation.tabs.rukucontenttab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.RukuContentStrategy;


/**
 * A simple {@link Fragment} subclass.
 */
public class RukuContentFragment extends Fragment {
    public RukuContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        int juzNumber = getArguments().getInt("juz number");

        RukuContentStrategy rukuContentStrategy = QuranSettings.getInstance().getMushafStrategy(getContext()).getRukuContentStrategy();

        RecyclerView juzRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);

        RukuContentAdapter adapter = rukuContentStrategy.getRukuContentAdapter(juzNumber, v);
        juzRecyclerView.setAdapter(adapter);

        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());

        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
