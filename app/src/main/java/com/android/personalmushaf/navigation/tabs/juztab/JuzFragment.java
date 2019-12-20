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


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzFragment extends Fragment {

    private View v;
    private RecyclerView juzRecyclerView;
    private JuzAdapter adapter;
    private String[] juzNames;
    private ActionBar actionBar;


    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab, container, false);

        actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        int mushafVersion = QuranSettings.getInstance().getMushafVersion(v.getContext());

        actionBar.setTitle("Qur'an Contents");

        this.juzNames = new String[30];
        System.arraycopy(v.getResources().getStringArray(R.array.juz_names), 0, juzNames, 0, 30);


        juzRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        adapter = new JuzAdapter(juzNames, mushafVersion);

        juzRecyclerView.setAdapter(adapter);
        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

}
