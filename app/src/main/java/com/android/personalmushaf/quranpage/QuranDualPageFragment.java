package com.android.personalmushaf.quranpage;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.model.Ayah;
import com.android.personalmushaf.model.AyahBounds;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranDualPageFragmentStrategy;
import com.android.personalmushaf.util.ImageUtils;

import java.util.List;

public class QuranDualPageFragment extends Fragment {


    private PageData leftPageData;
    private PageData rightPageData;
    private Ayah highlightedAyah = null;
    private boolean isHighlighted = false;
    private float x;
    private float y;

    public QuranDualPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page, container, false);

        int dualPagerPosition = getArguments().getInt("dual_pager_position");

        QuranDualPageFragmentStrategy quranDualPageFragmentStrategy = QuranSettings.getInstance().getMushafStrategy(getContext()).getQuranDualPageFragmentStrategy();

        final ImageView leftImage = v.findViewById(R.id.page1);
        final String leftPagePath = quranDualPageFragmentStrategy.getLeftPagePath(dualPagerPosition);
        leftPageData = quranDualPageFragmentStrategy.getLeftPageData(dualPagerPosition);

        final ImageView rightImage = v.findViewById(R.id.page2);
        final String rightPagePath = quranDualPageFragmentStrategy.getRightPagePath(dualPagerPosition);
        rightPageData = quranDualPageFragmentStrategy.getRightPageData(dualPagerPosition);

        loadImages(leftImage, rightImage, leftPagePath, rightPagePath);

        if (!quranDualPageFragmentStrategy.isDanglingPage(dualPagerPosition)) {
            setHighlight(leftImage, rightImage, leftPagePath, rightPagePath);
        } else {
            leftImage.setVisibility(View.GONE);
            setHighlightSingle(leftImage, rightPagePath, true);
            setHighlightSingle(rightImage, rightPagePath, true);
        }

        return v;
    }

    private void setHighlight(ImageView leftImage, ImageView rightImage, String leftPagePath, String rightPagePath) {
        setHighlightDual(leftImage, rightImage, leftPagePath, rightPagePath, false);
        setHighlightDual(rightImage, leftImage, rightPagePath, leftPagePath, true);
    }

    private void loadImages(ImageView leftImage, ImageView rightImage, String leftPagePath, String rightPagePath) {
        ImageUtils.getInstance().loadBitmap(rightPagePath, rightImage);
        ImageUtils.getInstance().loadBitmap(leftPagePath, leftImage);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setHighlightDual(final ImageView imageView, final ImageView otherImageView, final String path, final String otherPath, final boolean isRightPage) {
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
                Bitmap myBitmap = BitmapFactory.decodeFile(path);
                Bitmap myOtherBitmap = BitmapFactory.decodeFile(otherPath);

                Ayah ayah;
                if (isRightPage)
                    ayah = rightPageData.getAyahFromCoordinates(imageView, x, y);
                else
                    ayah = leftPageData.getAyahFromCoordinates(imageView, x, y);

                List<AyahBounds> ayahBounds = ayah.getAyahBounds();


                Paint myPaint = new Paint();
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setColor(Color.BLUE);
                myPaint.setAlpha(50);

                Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);


                //Draw the image bitmap into the canvas
                tempCanvas.drawBitmap(myBitmap, 0, 0, null);

                if (isHighlighted && ayah.equals(highlightedAyah)) {
                    highlightedAyah = null;
                    isHighlighted = false;
                }
                else {
                    highlightedAyah = ayah;
                    isHighlighted = true;
                    for (AyahBounds bounds: ayahBounds){
                        tempCanvas.drawRoundRect(bounds.getBounds(), 2, 2, myPaint);
                    }
                }


                imageView.setImageBitmap(tempBitmap);
                otherImageView.setImageBitmap(myOtherBitmap);

                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setHighlightSingle(final ImageView imageView, final String path, final boolean isRightPage) {
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
                Bitmap myBitmap = BitmapFactory.decodeFile(path);
                Ayah ayah;
                if (isRightPage)
                    ayah = rightPageData.getAyahFromCoordinates(imageView, x, y);
                else
                    ayah = leftPageData.getAyahFromCoordinates(imageView, x, y);

                List<AyahBounds> ayahBounds = ayah.getAyahBounds();


                Paint myPaint = new Paint();
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setColor(Color.BLUE);
                myPaint.setAlpha(50);

                Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);


                //Draw the image bitmap into the canvas
                tempCanvas.drawBitmap(myBitmap, 0, 0, null);

                if (isHighlighted && ayah.equals(highlightedAyah)) {
                    highlightedAyah = null;
                    isHighlighted = false;
                }
                else {
                    highlightedAyah = ayah;
                    isHighlighted = true;
                    for (AyahBounds bounds: ayahBounds){
                        tempCanvas.drawRoundRect(bounds.getBounds(), 2, 2, myPaint);
                    }
                }


                imageView.setImageBitmap(tempBitmap);

                return true;
            }
        });
    }
}
