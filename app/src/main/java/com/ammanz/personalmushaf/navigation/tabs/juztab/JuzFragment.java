package com.ammanz.personalmushaf.navigation.tabs.juztab;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;


/**
 * A simple {@link Fragment} subclass.
 */
public class JuzFragment extends Fragment {
    private QuranSettings quranSettings;

    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        quranSettings = QuranSettings.getInstance();

        MushafMetadata mushafMetadata = quranSettings.getMushafMetadata(getContext());


        RecyclerView juzRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);
        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());
        JuzAdapter adapter = getJuzAdapter(mushafMetadata);

        juzRecyclerView.setAdapter(adapter);
        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

    private JuzAdapter getJuzAdapter(MushafMetadata mushafMetadata) {
        return new JuzAdapter(getResources().getStringArray(R.array.juz_names),
                mushafMetadata.getNavigationData().getJuzPageNumbers(),
                mushafMetadata.getNavigationData().getJuzLengths());
    }

}
