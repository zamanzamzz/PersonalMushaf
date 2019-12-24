package com.android.personalmushaf.navigation;

import android.os.Environment;

public class QuranConstants {
    public static final String ASSETSDIRECTORY = Environment.getExternalStorageDirectory() + "/personal_mushaf";

    // number of surahs in juz      index of first surah in juz
    public static int[][] surahsInJuz = {
            {2, 0},
            {0, 0},
            {1, 2},
            {1, 3},
            {0, 0},
            {1, 4},
            {1, 5},
            {1, 6},
            {1, 7},
            {1, 8},
            {2, 9},
            {1, 11},
            {2, 12},
            {2, 14},
            {2, 16},
            {2, 18},
            {2, 20},
            {3, 22},
            {2, 25},
            {2, 27},
            {4, 29},
            {3, 33},
            {3, 36},
            {2, 39},
            {4, 41},
            {6, 45},
            {6, 51},
            {9, 57},
            {11, 66},
            {37, 77},
    };

    public static int[][] getSurahsInJuz(int[][] surahInfo, int juzNumber) {
        if (juzNumber < 0)
            return surahInfo;
        else {
            int[][] surahInfoInJuz;
            if (surahsInJuz[juzNumber-1][0] == 0) {
                surahInfoInJuz = new int[0][0];
                return surahInfoInJuz;
            } else {
                surahInfoInJuz = new int[surahsInJuz[juzNumber-1][0]][5];
                System.arraycopy(surahInfo, surahsInJuz[juzNumber-1][1], surahInfoInJuz, 0, surahsInJuz[juzNumber-1][0]);
                return surahInfoInJuz;
            }
        }
    }

    public static int getFirstSurahInJuz(int juzNumber) {
        return surahsInJuz[juzNumber - 1][1];
    }
}




