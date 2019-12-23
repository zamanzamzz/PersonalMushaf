package com.android.personalmushaf.navigation.tabs.juztab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.strategies.NavigationStrategy;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzFragment extends Fragment {

    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        actionBar.setTitle("Qur'an Contents");

        String[] juzNames = new String[30];
        System.arraycopy(v.getResources().getStringArray(R.array.juz_names), 0, juzNames, 0, 30);


        RecyclerView juzRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        NavigationStrategy navigationStrategy = QuranSettings.getInstance().getMushafStrategy(getContext()).getNavivationStrategy();
        JuzAdapter adapter = new JuzAdapter(juzNames, navigationStrategy);

        juzRecyclerView.setAdapter(adapter);
        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
