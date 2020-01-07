package com.android.personalmushaf.quranpage;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.model.Ayah;
import com.android.personalmushaf.model.HighlightType;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.util.ImageUtils;
import com.android.personalmushaf.widgets.HighlightingImageView;

public class QuranDualPageFragment extends QuranPage {

    private HighlightingImageView leftImage;
    private HighlightingImageView rightImage;
    private PageData leftPageData;
    private PageData rightPageData;
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

        int highlightedSurah = getArguments().getInt("highlighted_surah", 0);
        int highlightedAyah = getArguments().getInt("highlighted_ayah", 0);

        MushafMetadata mushafMetadata = QuranSettings.getInstance().getMushafMetadata(getContext());

        leftImage = v.findViewById(R.id.page1);
        final String leftPagePath = getLeftPagePath(dualPagerPosition, mushafMetadata);
        leftPageData = getLeftPageData(dualPagerPosition, mushafMetadata);

        rightImage = v.findViewById(R.id.page2);
        final String rightPagePath = getRightPagePath(dualPagerPosition, mushafMetadata);
        rightPageData = getRightPageData(dualPagerPosition, mushafMetadata);


        if (!isDanglingPage(dualPagerPosition, mushafMetadata)) {
            loadImages(leftImage, rightImage, leftPagePath, rightPagePath);
            setHighlightSingle(leftImage, leftPageData, dualPagerPosition);
            setHighlightSingle(rightImage, rightPageData, dualPagerPosition);
        } else {
            rightImage.setVisibility(View.GONE);
            ImageUtils.getInstance().loadBitmap(leftPagePath, leftImage);
            setHighlightSingle(leftImage, leftPageData, dualPagerPosition);
        }

        if (highlightedSurah != 0 && highlightedAyah != 0)
            highlightAyah(highlightedSurah, highlightedAyah, HighlightType.SELECTION);

        return v;
    }

    private void loadImages(ImageView leftImage, ImageView rightImage, String leftPagePath, String rightPagePath) {
        ImageUtils.getInstance().loadBitmap(rightPagePath, rightImage);
        ImageUtils.getInstance().loadBitmap(leftPagePath, leftImage);
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setHighlightSingle(final HighlightingImageView imageView, final PageData pageData, final int dualPagerPosition) {
        imageView.setAyahData(pageData.getAyahCoordinates());

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

                updateAdapter(ayah, dualPagerPosition);
                return true;
            }
        });
    }

    public void highlightAyah(int sura, int ayah, HighlightType highlightType) {
        rightImage.highlightAyah(sura, ayah, HighlightType.SELECTION);
        leftImage.highlightAyah(sura, ayah, HighlightType.SELECTION);
        rightImage.invalidate();
        leftImage.invalidate();
    }

    public void unhighlightAyah(int sura, int ayah, HighlightType highlightType) {
        rightImage.unHighlight(sura, ayah, HighlightType.SELECTION);
        leftImage.unHighlight(sura, ayah, HighlightType.SELECTION);
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



}
