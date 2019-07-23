package com.example.personalmushaf.navigation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NavigationDataUtil {
    public static String[][] fetchNavigationData(String[] colNames, String table, int juzNumber, boolean needLength) {
        String[][] dataSet;
        Cursor c;
        SQLiteDatabase database = SQLiteDatabase.openDatabase(QuranConstants.ASSETSDIRECTORY + "databases/quran_metadata.db", null, SQLiteDatabase.OPEN_READONLY);

        c = juzNumber < 0 ? database.query(table, colNames, null, null, null, null, null) :
                            database.query(table, colNames, "Juz = " + juzNumber, null, null, null, null);

        dataSet = new String[c.getCount()][5];
        int i = 0;
        if (c.moveToFirst()) {
            if (needLength) {
                do {
                    dataSet[i][0] = Integer.toString(c.getInt(0));
                    dataSet[i][1] = Integer.toString(c.getInt(1));
                    dataSet[i][2] = Integer.toString(c.getInt(2));
                    dataSet[i][3] = c.getString(3);
                    dataSet[i][4] = String.format("%.2f",c.getDouble(4));
                    i++;
                } while (c.moveToNext());
            } else {
                do {
                    dataSet[i][0] = Integer.toString(c.getInt(0));
                    dataSet[i][1] = Integer.toString(c.getInt(1));
                    dataSet[i][2] = Integer.toString(c.getInt(2));
                    dataSet[i][3] = c.getString(3);
                    i++;
                } while (c.moveToNext());
            }
        }
        c.close();
        database.close();

        return dataSet;
    }
}
