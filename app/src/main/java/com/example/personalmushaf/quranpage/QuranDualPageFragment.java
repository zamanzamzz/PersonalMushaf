package com.example.personalmushaf.quranpage;

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

import com.example.personalmushaf.QuranSettings;
import com.example.personalmushaf.R;
import com.example.personalmushaf.model.Ayah;
import com.example.personalmushaf.model.AyahBounds;
import com.example.personalmushaf.model.PageData;
import com.example.personalmushaf.navigation.QuranConstants;
import com.example.personalmushaf.util.ImageUtils;

import java.util.List;

public class QuranDualPageFragment extends Fragment {

    private int position;
    private View v;
    private PageData rightPageData;
    private PageData leftPageData;
    private Ayah highlightedAyah = null;
    private boolean isHighlighted = false;
    private float x;
    private float y;

    public QuranDualPageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_page, container, false);

        position = getArguments().getInt("page_number");

        String mushafVersion = QuranSettings.getInstance().getMushafVersion(v.getContext());

        final String rightPagePath;
        final String leftPagePath;

        if (!mushafVersion.equals("madani_15_line")) {
            if (position != 0 && position != 424) {
                final ImageView rightImage = v.findViewById(R.id.page2);
                final ImageView leftImage = v.findViewById(R.id.page1);


                rightPagePath = QuranConstants.ASSETSDIRECTORY + "13_line/13_pg_" + QuranConstants.naskh13LineDualPageSets[position][0] + ".png";
                leftPagePath = QuranConstants.ASSETSDIRECTORY + "13_line/13_pg_" + QuranConstants.naskh13LineDualPageSets[position][1] + ".png";

                ImageUtils.getInstance().loadBitmap(rightPagePath, rightImage);
                ImageUtils.getInstance().loadBitmap(leftPagePath, leftImage);

                setHighlightDual(leftImage, rightImage, leftPagePath, rightPagePath, false);
                setHighlightDual(rightImage, leftImage, rightPagePath, leftPagePath, true);
                rightPageData = new PageData(QuranConstants.naskh13LineDualPageSets[position][0],true);
                leftPageData = new PageData(QuranConstants.naskh13LineDualPageSets[position][1], true);


            } else {
                final ImageView image, image1;
                image = v.findViewById(R.id.page1);
                image1 = v.findViewById(R.id.page2);

                if (position == 424) {
                    image.setVisibility(View.GONE);

                    rightPagePath = QuranConstants.ASSETSDIRECTORY + "13_line/13_pg_" + QuranConstants.naskh13LineDualPageSets[position][0] + ".png";

                    ImageUtils.getInstance().loadBitmap(rightPagePath, image);
                    ImageUtils.getInstance().loadBitmap(rightPagePath, image1);

                    setHighlightSingle(image, rightPagePath, true);
                    setHighlightSingle(image1, rightPagePath, true);

                    rightPageData = new PageData(848, true);

                } else {
                    image1.setVisibility(View.GONE);

                    rightPagePath = QuranConstants.ASSETSDIRECTORY + "13_line/13_pg_" + QuranConstants.naskh13LineDualPageSets[position][0] + ".png";

                    ImageUtils.getInstance().loadBitmap(rightPagePath, image);
                    ImageUtils.getInstance().loadBitmap(rightPagePath, image1);
                }


            }

        }
        else {

            if (position < 302 && position >= 0) {
                leftPagePath = QuranConstants.ASSETSDIRECTORY + "15_line/pg_" + QuranConstants.madani15LineDualPageSets[position][1] + ".png";
                rightPagePath = QuranConstants.ASSETSDIRECTORY + "15_line/pg_" + QuranConstants.madani15LineDualPageSets[position][0] + ".png";

                final ImageView leftImage = v.findViewById(R.id.page1);
                final ImageView rightImage = v.findViewById(R.id.page2);

                ImageUtils.getInstance().loadBitmap(leftPagePath, leftImage);
                ImageUtils.getInstance().loadBitmap(rightPagePath, rightImage);

                setHighlightDual(leftImage, rightImage, leftPagePath, rightPagePath, false);
                setHighlightDual(rightImage, leftImage, rightPagePath, leftPagePath, true);
                leftPageData = new PageData(QuranConstants.madani15LineDualPageSets[position][1], false);
                rightPageData = new PageData(QuranConstants.madani15LineDualPageSets[position][0],false);
            }


        }

        return v;
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
