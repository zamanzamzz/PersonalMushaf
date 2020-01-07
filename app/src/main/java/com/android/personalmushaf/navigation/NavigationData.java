package com.android.personalmushaf.navigation;

public abstract class NavigationData {
    protected int[] juzPageNumbers;
    protected int[] surahPageNumbers;
    protected int[] quarterPageNumbers;
    protected int[] hizbPageNumbers;
    protected int[][] rukuPageNumbers;
    protected double[] juzLengths;
    protected double[] surahLengths;
    protected double[] quarterLengths;
    protected double[] hizbLengths;
    protected double[][] rukuLengths;

    public int[] getJuzPageNumbers() {
        return juzPageNumbers;
    }

    public int[] getSurahPageNumbers() {
        return surahPageNumbers;
    }

    public int[] getQuarterPageNumbers() {
        return quarterPageNumbers;
    }

    public int[] getHizbPageNumbers() {
        return hizbPageNumbers;
    }

    public int[][] getRukuPageNumbers() {
        return rukuPageNumbers;
    }

    public double[] getJuzLengths() {
        return juzLengths;
    }

    public double[] getSurahLengths() {
        return surahLengths;
    }

    public double[] getQuarterLengths() {
        return quarterLengths;
    }

    public double[] getHizbLengths() {
        return hizbLengths;
    }

    public double[][] getRukuLengths() {
        return rukuLengths;
    }
}
