package com.android.personalmushaf.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.RectF;
import android.util.SparseArray;
import android.widget.ImageView;

import com.android.personalmushaf.navigation.navigationdata.QuranConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PageData {
    private static final String COL_PAGE = "page_number";
    private static final String COL_LINE = "line_number";
    private static final String COL_SURA = "sura_number";
    private static final String COL_AYAH = "ayah_number";
    private static final String COL_POSITION = "position";
    private static final String MIN_X = "min_x";
    private static final String MIN_Y = "min_y";
    private static final String MAX_X = "max_x";
    private static final String MAX_Y = "max_y";
    private static final String GLYPHS_TABLE = "glyphs";
    private Map<String, List<AyahBounds>> ayahBounds;

    public PageData(final int pageNumber, final boolean mushaf) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ayahBounds = new HashMap<>();
                String path = !mushaf ? QuranConstants.ASSETSDIRECTORY + "/madani_15_line/databases/ayahinfo_15line.db":
                                        QuranConstants.ASSETSDIRECTORY + "/naskh_13_line/databases/ayahinfo_13line.db";
                SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
                Cursor c = database.query(GLYPHS_TABLE,
                        new String[]{COL_PAGE, COL_LINE, COL_SURA, COL_AYAH,
                                COL_POSITION, MIN_X, MIN_Y, MAX_X, MAX_Y},
                        COL_PAGE + "=" + pageNumber,
                        null, null, null,
                        COL_SURA + "," + COL_AYAH + "," + COL_POSITION);
                try {
                    while (c.moveToNext()) {
                        int sura = c.getInt(2);
                        int ayah = c.getInt(3);
                        String key = sura + ":" + ayah;
                        List<AyahBounds> bounds = ayahBounds.get(key);
                        if (bounds == null) {
                            bounds = new ArrayList<>();
                        }

                        AyahBounds last = null;
                        if (bounds.size() > 0) {
                            last = bounds.get(bounds.size() - 1);
                        }

                        AyahBounds bound = new AyahBounds(c.getInt(1),
                                c.getInt(4), c.getInt(5),
                                c.getInt(6), c.getInt(7),
                                c.getInt(8));
                        if (last != null && last.getLine() == bound.getLine()) {
                            last.engulf(bound);
                        } else {
                            bounds.add(bound);
                        }
                        ayahBounds.put(key, bounds);
                    }
                } finally {
                    c.close();
                    database.close();
                }
            }
        }).run();
    }

    public Ayah getAyahFromCoordinates(
            ImageView imageView, float xc, float yc) {
        if (ayahBounds == null || imageView == null){ return null; }

        /*float[] pageXY = getPageXY(xc, yc, imageView);
        if (pageXY == null){ return null; }
        float xc = pageXY[0];
        float yc = pageXY[1];*/

        int closestLine = -1;
        int closestDelta = -1;

        final SparseArray<List<String>> lineAyahs = new SparseArray<>();
        final Set<String> keys = ayahBounds.keySet();
        for (String key : keys){
            List<AyahBounds> bounds = ayahBounds.get(key);
            if (bounds == null){ continue; }

            for (AyahBounds b : bounds){
                // only one AyahBound will exist for an ayah on a particular line
                int line = b.getLine();
                List<String> items = lineAyahs.get(line);
                if (items == null){
                    items = new ArrayList<>();
                }
                items.add(key);
                lineAyahs.put(line, items);

                final RectF boundsRect = b.getBounds();
                if (boundsRect.contains(xc, yc)) {
                    return getAyahFromKey(key);
                }

                int delta = Math.min((int) Math.abs(boundsRect.bottom - yc),
                        (int) Math.abs(boundsRect.top - yc));
                if (closestDelta == -1 || delta < closestDelta){
                    closestLine = b.getLine();
                    closestDelta = delta;
                }
            }
        }

        if (closestLine > -1){
            int leastDeltaX = -1;
            String closestAyah = null;
            List<String> ayat = lineAyahs.get(closestLine);
            if (ayat != null){
                for (String ayah : ayat){
                    List<AyahBounds> bounds = ayahBounds.get(ayah);
                    if (bounds == null){ continue; }
                    for (AyahBounds b : bounds){
                        if (b.getLine() > closestLine){
                            // this is the last ayah in ayat list
                            break;
                        }

                        final RectF boundsRect = b.getBounds();
                        if (b.getLine() == closestLine){
                            // if xc is within the xc of this ayah, that's our answer
                            if (boundsRect.right >= xc && boundsRect.left <= xc){
                                return getAyahFromKey(ayah);
                            }

                            // otherwise, keep track of the least delta and return it
                            int delta = Math.min((int) Math.abs(boundsRect.right - xc),
                                    (int) Math.abs(boundsRect.left - xc));
                            if (leastDeltaX == -1 || delta < leastDeltaX){
                                closestAyah = ayah;
                                leastDeltaX = delta;
                            }
                        }
                    }
                }
            }

            if (closestAyah != null){
                return getAyahFromKey(closestAyah);
            }
        }
        return null;
    }

    private Ayah getAyahFromKey(String key){
        List<AyahBounds> bounds = ayahBounds.get(key);
        return new Ayah(key, bounds);
    }


}
