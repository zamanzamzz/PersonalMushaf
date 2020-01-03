package com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies;

import static com.android.personalmushaf.navigation.QuranConstants.surahInfo;
import static com.android.personalmushaf.navigation.QuranConstants.surahsInJuz;

public abstract class AbstractSurahStrategy {

    private int getFirstSurahInJuz(int juzNumber) {
        return surahsInJuz[juzNumber - 1][1];
    }

    protected int[][] getSurahInfoInJuz(int juzNumber) {
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

    protected int[] getSurahPageNumbersinJuz(int[] source, int juzNumber) {
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

    protected String[] getSurahNamesInJuz(int juzNumber, int numOfSurahs, String[] source) {
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

    protected double[] getSurahLengthsInJuz(double[] source, int juzNumber) {
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
