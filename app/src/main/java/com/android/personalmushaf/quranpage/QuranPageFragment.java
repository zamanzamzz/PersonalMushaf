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
import com.android.personalmushaf.mushafinterfaces.strategies.quranstrategies.QuranPageFragmentStrategy;
import com.android.personalmushaf.util.ImageUtils;

import java.util.List;

public class QuranPageFragment extends Fragment {
    private PageData pageData;
    private Ayah highlightedAyah = null;
    private boolean isHighlighted = false;
    private float x;
    private float y;

    public QuranPageFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page, container, false);

        int pageNumber = getArguments().getInt("page_number");

        ImageView imageView = v.findViewById(R.id.page1);

        QuranPageFragmentStrategy quranPageFragmentStrategy = QuranSettings.getInstance().getMushafStrategy(imageView.getContext()).getQuranPageFragmentStrategy();
        String path = quranPageFragmentStrategy.getPagePath(pageNumber);
        pageData = quranPageFragmentStrategy.getPageData(pageNumber);
        ImageUtils.getInstance().loadBitmap(path, imageView);
        setHighlight(imageView, path);

        return v;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setHighlight(final ImageView imageView, final String path) {
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
