package com.android.personalmushaf.mushafinterfaces.strategies.abstractnavigationstrategies;

public abstract class AbstractJuzQuarterStrategy {

    protected int[][] getQuarterInfo(int juzNumber, int[][] source, int numOfQuarters) {
        int[][] quarterInfo = new int[numOfQuarters][3];
        System.arraycopy(source, (juzNumber - 1) * numOfQuarters, quarterInfo, 0, numOfQuarters);
        return quarterInfo;
    }

    protected int[] getQuarterPageNumbers(int juzNumber, int[] source, int numOfQuarters) {
        int[] quarterPageNumbers = new int[numOfQuarters];
        System.arraycopy(source, (juzNumber - 1) * numOfQuarters, quarterPageNumbers, 0, numOfQuarters);
        return quarterPageNumbers;
    }

    protected double[] getQuarterLengths(int juzNumber, double[] source, int numOfQuarters) {
        double[] quarterLengths = new double[numOfQuarters];
        System.arraycopy(source, (juzNumber - 1) * numOfQuarters, quarterLengths, 0, numOfQuarters);
        return quarterLengths;
    }

    protected String[] getQuarterStringResource(int juzNumber, String[] source, int numOfQuarters) {
        String[] quarterPrefixes = new String[numOfQuarters];
        System.arraycopy(source, (juzNumber - 1)*numOfQuarters, quarterPrefixes, 0, numOfQuarters);
        return quarterPrefixes;
    }
}
