package com.ammanz.personalmushaf.quranpage;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.model.Ayah;
import com.ammanz.personalmushaf.model.HighlightType;
import com.ammanz.personalmushaf.model.PageData;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.widgets.HighlightingImageView;

import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuranPageFragment extends QuranPage {

    private HighlightingImageView imageView;
    private PageData pageData;
    private float x;
    private float y;
    private QuranSettings quranSettings;

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

        ((LinearLayout) v.findViewById(R.id.pager_linear_layout)).setOrientation(LinearLayout.VERTICAL);
        v.findViewById(R.id.page2).setVisibility(View.GONE);

        quranSettings = QuranSettings.getInstance();

        int pageNumber = getArguments().getInt("page_number");
        int position = getArguments().getInt("position");

        int highlightedSurah = getArguments().getInt("highlighted_surah", 0);
        int highlightedAyah = getArguments().getInt("highlighted_ayah", 0);

        imageView = v.findViewById(R.id.page1);

        MushafMetadata mushafMetadata = quranSettings.getMushafMetadata(getContext());
        String path = getPagePath(pageNumber, mushafMetadata);
        pageData = getPageData(pageNumber, mushafMetadata);

        AtomicReference<Bitmap> bitmap = new AtomicReference<>();
        Observable.fromCallable(() -> {
            pageData.populateAyahBoundsAndGlyphs();
            bitmap.set(BitmapFactory.decodeFile(path));
            return true;
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
                imageView.setImageBitmap(bitmap.get());
                imageView.setAyahData(pageData.getAyahCoordinates());
                imageView.setGlyphs(pageData.getGlyphs());

                setHighlight(imageView, position);

                if (highlightedSurah != 0 && highlightedAyah != 0)
                    highlightAyah(highlightedSurah, highlightedAyah, HighlightType.SELECTION);
        });

        return v;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setHighlight(final HighlightingImageView imageView, final int position) {
        final Matrix inverse = new Matrix();

        imageView.setOnTouchListener((v, event) -> {
            imageView.getImageMatrix().invert(inverse);
            float[] pts = {event.getX(), event.getY()};
            inverse.mapPoints(pts);
            x = pts[0];
            y = pts[1];
            return false;
        });

        imageView.setLongClickable(true);

        imageView.setOnLongClickListener(v -> {
            Ayah ayah = pageData.getAyahFromCoordinates(imageView, x, y);

            updateQuranActivity(ayah, position);
            return true;
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
