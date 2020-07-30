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
    private RecyclerView juzRecyclerView;
    private LinearLayoutManager juzLayoutManager;

    public JuzFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        quranSettings = QuranSettings.getInstance();

        MushafMetadata mushafMetadata = quranSettings.getMushafMetadata(getContext());

        juzRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);
        juzLayoutManager = new LinearLayoutManager(getContext());
        JuzAdapter adapter = getJuzAdapter(mushafMetadata);

        juzRecyclerView.setAdapter(adapter);
        juzRecyclerView.setLayoutManager(juzLayoutManager);

        Bundle arguments = getArguments();
        int juzPosition = arguments.getInt("juz_position", -1);
        int juzOffset = arguments.getInt("juz_offset", -1);

        if (juzPosition != -1 && juzOffset != -1)
            juzRecyclerView.post(() -> {
                juzLayoutManager.scrollToPositionWithOffset(juzPosition, juzOffset);
            });

        return v;
    }

    public int getJuzPosition() {
        return juzLayoutManager.findFirstVisibleItemPosition();
    }

    public int getJuzOffset() {
        View v = juzLayoutManager.getChildAt(0);
        return (v == null) ? 0 : (v.getTop() - juzLayoutManager.getPaddingTop());
    }

    private JuzAdapter getJuzAdapter(MushafMetadata mushafMetadata) {
        return new JuzAdapter(getResources().getStringArray(R.array.juz_names),
                mushafMetadata.getNavigationData().getJuzPageNumbers(),
                mushafMetadata.getNavigationData().getJuzLengths());
    }

}
