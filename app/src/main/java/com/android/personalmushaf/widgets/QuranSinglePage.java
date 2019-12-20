package com.android.personalmushaf.widgets;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.model.Ayah;
import com.android.personalmushaf.model.AyahBounds;
import com.android.personalmushaf.model.PageData;
import com.android.personalmushaf.navigation.navigationdata.QuranConstants;
import com.android.personalmushaf.util.ImageUtils;

import java.util.List;

public class QuranSinglePage {
    private ImageView imageView;
    private int pageNumber;
    private PageData pageData;
    private Ayah highlightedAyah = null;
    private boolean isHighlighted = false;
    private float x;
    private float y;

    public QuranSinglePage(ImageView imageView, int pageNumber) {
        this.imageView = imageView;
        this.pageNumber = pageNumber;
    }


    public void init() {
        String path;
        int mushafVersion = QuranSettings.getInstance().getMushafVersion(imageView.getContext());

        if (mushafVersion == QuranSettings.MADANI15LINE) {
            path = QuranConstants.ASSETSDIRECTORY + "/madani_15_line/images/pg_" + pageNumber + ".png";
            ImageUtils.getInstance().loadBitmap(path, imageView);
            setPageData(new PageData(pageNumber, false));
            setHighlight(path);
        }
        else {

            path = QuranConstants.ASSETSDIRECTORY + "/naskh_13_line/images/pg_" + pageNumber + ".png";

            ImageUtils.getInstance().loadBitmap(path, imageView);

            if (pageNumber != 1) {
                setPageData(new PageData(pageNumber, true));
                setHighlight(path);
            }

        }
    }

    private void setPageData(PageData pageData) {
        this.pageData = pageData;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setHighlight(final String path) {
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
                Ayah ayah = pageData.getAyahFromCoordinates(imageView, x, y);
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
