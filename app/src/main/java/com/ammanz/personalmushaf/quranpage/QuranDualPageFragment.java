package com.ammanz.personalmushaf.quranpage;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.model.Ayah;
import com.ammanz.personalmushaf.model.HighlightType;
import com.ammanz.personalmushaf.model.PageData;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.util.ImageUtils;
import com.ammanz.personalmushaf.widgets.HighlightingImageView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuranDualPageFragment extends QuranPage {

    private HighlightingImageView rightImage;
    private HighlightingImageView leftImage;
    private PageData leftPageData;
    private PageData rightPageData;
    private int highlightedSurah;
    private int highlightedAyah;
    private float x;
    private float y;

    public static QuranDualPageFragment newInstance(int position, Integer highlightedSurah, Integer highlightedAyah) {
        QuranDualPageFragment fragment = new QuranDualPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("dual_pager_position", position);
        if (highlightedSurah != null && highlightedAyah != null) {
            bundle.putInt("highlighted_surah", highlightedSurah);
            bundle.putInt("highlighted_ayah", highlightedAyah);
        }
        fragment.setArguments(bundle);

        return fragment;
    }

    public QuranDualPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page, container, false);

        int dualPagerPosition = getArguments().getInt("dual_pager_position");

        highlightedSurah = getArguments().getInt("highlighted_surah", 0);
        highlightedAyah = getArguments().getInt("highlighted_ayah", 0);

        MushafMetadata mushafMetadata = QuranSettings.getInstance().getMushafMetadata(getContext());

        rightImage = v.findViewById(R.id.page1);
        final String leftPagePath = getLeftPagePath(dualPagerPosition, mushafMetadata);
        leftPageData = getLeftPageData(dualPagerPosition, mushafMetadata);

        leftImage = v.findViewById(R.id.page2);
        final String rightPagePath = getRightPagePath(dualPagerPosition, mushafMetadata);
        rightPageData = getRightPageData(dualPagerPosition, mushafMetadata);


        if (!isDanglingPage(dualPagerPosition, mushafMetadata)) {
            loadImages(rightImage, leftImage, leftPagePath, rightPagePath);
            setHighlightSingle(rightImage, leftPageData, dualPagerPosition);
            setHighlightSingle(leftImage, rightPageData, dualPagerPosition);
        } else {
            leftImage.setVisibility(View.GONE);
            ImageUtils.loadBitmap(leftPagePath, rightImage);
            setHighlightSingle(rightImage, leftPageData, dualPagerPosition);
        }

        return v;
    }

    private void loadImages(ImageView leftImage, ImageView rightImage, String leftPagePath, String rightPagePath) {
        ImageUtils.loadBitmap(rightPagePath, rightImage);
        ImageUtils.loadBitmap(leftPagePath, leftImage);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setHighlightSingle(final HighlightingImageView imageView, final PageData pageData, final int dualPagerPosition) {
        imageView.setAyahData(pageData.getAyahCoordinates());
        imageView.setGlyphs(pageData.getGlyphs());

        Observable.fromCallable(() -> {
            pageData.populateAyahBoundsAndGlyphs();
            return true;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {
            imageView.setAyahData(pageData.getAyahCoordinates());
            imageView.setGlyphs(pageData.getGlyphs());

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

                updateQuranActivity(ayah, dualPagerPosition);
                return true;
            });

            if (highlightedSurah != 0 && highlightedAyah != 0)
                highlightAyah(highlightedSurah, highlightedAyah, HighlightType.SELECTION);
        });
    }

    public void highlightAyah(int sura, int ayah, HighlightType highlightType) {
        leftImage.highlightAyah(sura, ayah, HighlightType.SELECTION);
        rightImage.highlightAyah(sura, ayah, HighlightType.SELECTION);
        leftImage.invalidate();
        rightImage.invalidate();
    }

    public void unhighlightAyah(int sura, int ayah, HighlightType highlightType) {
        leftImage.unHighlight(sura, ayah, HighlightType.SELECTION);
        rightImage.unHighlight(sura, ayah, HighlightType.SELECTION);
    }

    private String getLeftPagePath(int dualPagerPosition, MushafMetadata mushafMetadata) {
        return getPagePath(dualPagerPositionToPageNumber(dualPagerPosition, mushafMetadata), mushafMetadata);
    }

    private String getRightPagePath(int dualPagerPosition, MushafMetadata mushafMetadata) {
        return getPagePath(dualPagerPositionToPageNumber(dualPagerPosition, mushafMetadata) + 1, mushafMetadata);
    }

    private PageData getLeftPageData(int dualPagerPosition, MushafMetadata mushafMetadata) {
        return getPageData(dualPagerPositionToPageNumber(dualPagerPosition, mushafMetadata), mushafMetadata);
    }

    private PageData getRightPageData(int dualPagerPosition, MushafMetadata mushafMetadata) {
        return getPageData(dualPagerPositionToPageNumber(dualPagerPosition, mushafMetadata) + 1, mushafMetadata);
    }

    private boolean isDanglingPage(int dualPagerPosition, MushafMetadata mushafMetadata) {
        if (mushafMetadata.getDanglingDualPage() < 0)
            return false;
        else
            return dualPagerPosition == mushafMetadata.getDanglingDualPage();
    }

    private int dualPagerPositionToPageNumber(int dualPagerPosition, MushafMetadata mushafMetadata) {
        return dualPagerPosition * 2 + mushafMetadata.getMinPage();
    }

    @Override
    public void highlightGlyph(int glyphIndex) {
        if (glyphIndex < 0) {
            rightImage.drawGlyph(-1);
            leftImage.drawGlyph(-1);
            return;
        }

        if (glyphIndex < leftImage.getNumOfGlyphs())
            rightImage.drawGlyph(glyphIndex);
        else {
            rightImage.drawGlyph(-1);
            leftImage.drawGlyph(glyphIndex - rightImage.getNumOfGlyphs());
        }
    }

    @Override
    public int getNumOfGlyphs() {
        return leftImage.getNumOfGlyphs() + rightImage.getNumOfGlyphs();
    }
}
