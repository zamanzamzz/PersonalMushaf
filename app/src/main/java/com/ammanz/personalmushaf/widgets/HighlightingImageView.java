package com.ammanz.personalmushaf.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.model.AyahBounds;
import com.ammanz.personalmushaf.model.AyahCoordinates;
import com.ammanz.personalmushaf.model.HighlightType;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class HighlightingImageView extends AppCompatImageView {

    private static final SparseArray<Paint> SPARSE_PAINT_ARRAY = new SparseArray<>();


    // Sorted map so we use highest priority highlighting when iterating
    private SortedMap<HighlightType, Set<String>> currentHighlights = new TreeMap<>();
    private static final int nightModeTextBrightness = 255;
    private float[] nightModeMatrix = {
            -1, 0, 0, 0, nightModeTextBrightness,
            0, -1, 0, 0, nightModeTextBrightness,
            0, 0, -1, 0, nightModeTextBrightness,
            0, 0, 0, 1, 0
    };



    // cached objects for onDraw
    private final RectF scaledRect = new RectF();
    private final Set<String> alreadyHighlighted = new HashSet<>();

    // Params for drawing text
    private AyahCoordinates ayahCoordinates;

    private List<AyahBounds> glyphs;
    private int currentGlyph = -1;
    private QuranSettings quranSettings;

    public HighlightingImageView(Context context) {
        this(context, null);
        quranSettings = QuranSettings.getInstance();
        if (quranSettings.getNightMode(getContext()))
            setColorFilter(new ColorMatrixColorFilter(nightModeMatrix));
    }

    public HighlightingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        quranSettings = QuranSettings.getInstance();
        if (quranSettings.getNightMode(getContext()))
            setColorFilter(new ColorMatrixColorFilter(nightModeMatrix));
    }


    public void unHighlight(int sura, int ayah, HighlightType type) {
        Set<String> highlights = currentHighlights.get(type);
        if (highlights != null && highlights.remove(sura + ":" + ayah)) {
            invalidate();
        }
    }

    public void highlightAyat(Set<String> ayahKeys, HighlightType type) {
        Set<String> highlights = currentHighlights.get(type);
        if (highlights == null) {
            highlights = new HashSet<>();
            currentHighlights.put(type, highlights);
        }
        highlights.addAll(ayahKeys);
    }

    public void unHighlight(HighlightType type) {
        if (!currentHighlights.isEmpty()) {
            currentHighlights.remove(type);
            invalidate();
        }
    }



    public void setAyahData(AyahCoordinates ayahCoordinates) {
        this.ayahCoordinates = ayahCoordinates;
    }

    public void setGlyphs(List<AyahBounds> glyphs) {
        this.glyphs = glyphs;
    }

    public int getNumOfGlyphs() {
        return glyphs.size();
    }

    public void drawGlyph(int glyphIndex) {
        currentGlyph = glyphIndex;
        invalidate();
    }


    public void highlightAyah(int sura, int ayah, HighlightType type) {
        Set<String> highlights = currentHighlights.get(type);
        if (highlights == null) {
            highlights = new HashSet<>();
            currentHighlights.put(type, highlights);
        } else if (!type.isMultipleHighlightsAllowed()) {
            // If multiple highlighting not allowed (e.g. audio)
            // clear all others of this type first
            highlights.clear();
        }
        highlights.add(sura + ":" + ayah);
    }

    private Paint getPaintForHighlightType(HighlightType type) {
        int color = type.getColor(getContext());
        Paint paint = SPARSE_PAINT_ARRAY.get(color);
        if (paint == null) {
            paint = new Paint();
            paint.setColor(color);
            SPARSE_PAINT_ARRAY.put(color, paint);
        }
        return paint;
    }


    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (getDrawable() == null) {
            // no image, forget it.
            return;
        }

        if (currentGlyph > -1 && currentGlyph < getNumOfGlyphs()) {
            drawAyahBounds(glyphs.get(currentGlyph), getImageMatrix(), canvas, getPaintForHighlightType(HighlightType.SELECTION));
        }

        drawHighlights(canvas);
    }

    private void drawHighlights(Canvas canvas) {
        final Matrix matrix = getImageMatrix();

        // Draw each ayah highlight
        final Map<String, List<AyahBounds>> coordinatesData = ayahCoordinates == null ? null :
                ayahCoordinates.getAyahCoordinates();

        if (coordinatesData != null && !currentHighlights.isEmpty()) {
            alreadyHighlighted.clear();
            for (Map.Entry<HighlightType, Set<String>> entry : currentHighlights.entrySet()) {
                drawAyaat(entry, coordinatesData, matrix, canvas);
            }
        }
    }

    private void drawAyaat(Map.Entry<HighlightType, Set<String>> entry, Map<String,
                            List<AyahBounds>> coordinatesData,
                            Matrix matrix, Canvas canvas) {
        Paint paint = getPaintForHighlightType(entry.getKey());
        for (String ayah : entry.getValue()) {
            if (alreadyHighlighted.contains(ayah)) continue;
            drawAyah(coordinatesData, ayah, matrix, canvas, paint);
        }
    }

    private void drawAyah(Map<String, List<AyahBounds>> coordinatesData, String ayah,
                          Matrix matrix, Canvas canvas, Paint paint) {
        List<AyahBounds> rangesToDraw = coordinatesData.get(ayah);
        if (rangesToDraw != null && !rangesToDraw.isEmpty()) {
            drawRange(ayah, rangesToDraw, matrix, canvas, paint);
        }
    }

    private void drawRange(String ayah, List<AyahBounds> rangesToDraw,
                           Matrix matrix, Canvas canvas, Paint paint) {
        for (AyahBounds b : rangesToDraw) {
            drawAyahBounds(b, matrix, canvas, paint);
        }
        alreadyHighlighted.add(ayah);
    }

    private void drawAyahBounds(AyahBounds ayahBounds, Matrix matrix, Canvas canvas, Paint paint) {
        matrix.mapRect(scaledRect, ayahBounds.getBounds());
        scaledRect.offset(0, getPaddingTop());
        canvas.drawRect(scaledRect, paint);
    }
}
