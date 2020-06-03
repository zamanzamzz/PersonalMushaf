package com.ammanz.personalmushaf.navigation.tabs.surahtab;


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

import static com.ammanz.personalmushaf.navigation.QuranConstants.surahInfo;
import static com.ammanz.personalmushaf.navigation.QuranConstants.surahsInJuz;


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
        MushafMetadata mushafMetadata = QuranSettings.getInstance().getMushafMetadata(getContext());

        View v = inflater.inflate(R.layout.fragment_tab, container, false);
        RecyclerView surahRecyclerView = v.findViewById(R.id.tab_recycler_view);
        surahRecyclerView.setHasFixedSize(true);
        LinearLayoutManager surahLayoutManager = new LinearLayoutManager(getContext());

        SurahAdapter adapter = getSurahAdapter(juzNumber, mushafMetadata);
        surahRecyclerView.setAdapter(adapter);
        surahRecyclerView.setLayoutManager(surahLayoutManager);

        return v;
    }

    private SurahAdapter getSurahAdapter(int juzNumber, MushafMetadata mushafMetadata) {
        int[][] surahInfo = getSurahInfoInJuz(juzNumber);
        int[] surahPageNumbersInJuz = getSurahPageNumbersinJuz(mushafMetadata.getNavigationData().getSurahPageNumbers(), juzNumber);
        String[] surahNamesInJuz = QuranSettings.getInstance().getSimplifyInterface(getContext()) ?
                getSurahNamesInJuz(juzNumber, surahInfo.length, getResources().getStringArray(R.array.surah_names_english)) :
                getSurahNamesInJuz(juzNumber, surahInfo.length, getResources().getStringArray(R.array.surah_names_arabic));

        double[] surahLengthsInJuz = getSurahLengthsInJuz(mushafMetadata.getNavigationData().getSurahLengths(), juzNumber);

        return new SurahAdapter(surahInfo, surahPageNumbersInJuz, surahNamesInJuz, surahLengthsInJuz);
    }

    private int getFirstSurahInJuz(int juzNumber) {
        return surahsInJuz[juzNumber - 1][1];
    }

    private int[][] getSurahInfoInJuz(int juzNumber) {
        if (juzNumber < 0)
            return surahInfo;
        else {
            int[][] surahInfoInJuz;
            if (surahsInJuz[juzNumber-1][0] == 0) {
                surahInfoInJuz = new int[0][0];
                return surahInfoInJuz;
            } else {
                surahInfoInJuz = new int[surahsInJuz[juzNumber-1][0]][2];
                System.arraycopy(surahInfo, surahsInJuz[juzNumber-1][1], surahInfoInJuz, 0, surahsInJuz[juzNumber-1][0]);
                return surahInfoInJuz;
            }
        }
    }

    private int[] getSurahPageNumbersinJuz(int[] source, int juzNumber) {
        if (juzNumber < 0)
            return source;
        else {
            int[] surahPageNumbersInJuz;
            if (surahsInJuz[juzNumber-1][0] == 0) {
                surahPageNumbersInJuz = new int[0];
                return surahPageNumbersInJuz;
            } else {
                surahPageNumbersInJuz = new int[surahsInJuz[juzNumber-1][0]];
                System.arraycopy(source, surahsInJuz[juzNumber-1][1], surahPageNumbersInJuz, 0, surahsInJuz[juzNumber-1][0]);
                return surahPageNumbersInJuz;
            }
        }
    }

    private String[] getSurahNamesInJuz(int juzNumber, int numOfSurahs, String[] source) {
        String[] surahNames = new String[numOfSurahs];

        if (numOfSurahs > 0) {
            int firstSurahInJuz;
            if (juzNumber > 0)
                firstSurahInJuz = getFirstSurahInJuz(juzNumber);
            else
                firstSurahInJuz = 0;

            System.arraycopy(source, firstSurahInJuz, surahNames, 0, numOfSurahs);
        }

        return surahNames;
    }

    private double[] getSurahLengthsInJuz(double[] source, int juzNumber) {
        if (juzNumber < 0)
            return source;
        else {
            double[] surahLengthsInJuz;
            if (surahsInJuz[juzNumber-1][0] == 0) {
                surahLengthsInJuz = new double[0];
                return surahLengthsInJuz;
            } else {
                surahLengthsInJuz = new double[surahsInJuz[juzNumber-1][0]];
                System.arraycopy(source, surahsInJuz[juzNumber-1][1], surahLengthsInJuz, 0, surahsInJuz[juzNumber-1][0]);
                return surahLengthsInJuz;
            }
        }
    }


}
