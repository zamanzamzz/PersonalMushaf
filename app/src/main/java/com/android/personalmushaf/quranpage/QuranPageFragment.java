package com.android.personalmushaf.quranpage;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.model.Ayah;
import com.android.personalmushaf.model.HighlightType;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.ImageUtils;
import com.android.personalmushaf.widgets.HighlightingImageView;

public class QuranPageFragment extends QuranPage {

    private HighlightingImageView imageView;
    private PageData pageData;
    private float x;
    private float y;

    public static QuranPageFragment newInstance(int pageNumber, int position, Integer highlightedSurah, Integer highlightedAyah) {
        QuranPageFragment fragment = new QuranPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("page_number", pageNumber);
        bundle.putInt("position", position);

        if (highlightedSurah != null && highlightedAyah != null) {
            bundle.putInt("highlighted_surah", highlightedSurah);
            bundle.putInt("highlighted_ayah", highlightedAyah);
        }

        fragment.setArguments(bundle);

        return fragment;
    }

    public QuranPageFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page, container, false);

        int pageNumber = getArguments().getInt("page_number");
        int position = getArguments().getInt("position");

        int highlightedSurah = getArguments().getInt("highlighted_surah", 0);
        int highlightedAyah = getArguments().getInt("highlighted_ayah", 0);

        imageView = v.findViewById(R.id.page1);

        MushafMetadata mushafMetadata = QuranSettings.getInstance().getMushafMetadata(getContext());
        String path = getPagePath(pageNumber, mushafMetadata);
        pageData = getPageData(pageNumber, mushafMetadata);
        imageView.setAyahData(pageData.getAyahCoordinates());
        imageView.setGlyphs(pageData.getGlyphs());
        ImageUtils.getInstance().loadBitmap(path, imageView);
        setHighlight(imageView, position);

        if (highlightedSurah != 0 && highlightedAyah != 0)
            highlightAyah(highlightedSurah, highlightedAyah, HighlightType.SELECTION);

        return v;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setHighlight(final HighlightingImageView imageView, final int position) {
        final Matrix inverse = new Matrix();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imageView.getImageMatrix().invert(inverse);
                float[] pts = {event.getX(), event.getY()};
                inverse.mapPoints(pts);
                x = pts[0];
                y = pts[1];
                return false;
            }
        });

        imageView.setLongClickable(true);

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Ayah ayah = pageData.getAyahFromCoordinates(imageView, x, y);

                updateAdapter(ayah, position);
                return true;
            }
        });
    }

    @Override
    public void highlightAyah(int sura, int ayah, HighlightType highlightType) {
        imageView.highlightAyah(sura, ayah, HighlightType.SELECTION);
        imageView.invalidate();
    }

    @Override
    public void unhighlightAyah(int sura, int ayah, HighlightType highlightType) {
        imageView.unHighlight(sura, ayah, HighlightType.SELECTION);
    }

    @Override
    public void highlightGlyph(int glyphIndex) {
        imageView.drawGlyph(glyphIndex);
    }

    @Override
    public int getNumOfGlyphs() {
        return imageView.getNumOfGlyphs();
    }
}
