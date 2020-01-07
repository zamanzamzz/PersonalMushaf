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
import com.android.personalmushaf.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.navigation.QuranConstants;


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

        MushafMetadata mushafMetadata = QuranSettings.getInstance().getMushafMetadata(getContext());

        RecyclerView juzRecyclerView = v.findViewById(R.id.tab_recycler_view);
        juzRecyclerView.setHasFixedSize(true);

        RukuContentAdapter adapter = getRukuContentAdapter(juzNumber, mushafMetadata);
        juzRecyclerView.setAdapter(adapter);

        LinearLayoutManager juzLayoutManager = new LinearLayoutManager(getContext());

        juzRecyclerView.setLayoutManager(juzLayoutManager);
        return v;
    }

    private RukuContentAdapter getRukuContentAdapter(int juzNumber, MushafMetadata mushafMetadata) {
        int[][] rukuInfo = QuranConstants.rukuInfo[(juzNumber - 1)];
        int[] rukuPageNumbers = mushafMetadata.getNavigationData().getRukuPageNumbers()[juzNumber - 1];
        double[] rukuLengths = mushafMetadata.getNavigationData().getRukuLengths()[juzNumber - 1];
        String[] prefixes = getResources().getStringArray(getResources().getIdentifier("juz_" + ((juzNumber - 1)), "array", getContext().getPackageName()));
        return new RukuContentAdapter(rukuInfo, rukuPageNumbers, rukuLengths, prefixes);
    }

}
