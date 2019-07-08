package com.example.personalmushaf.quranpage;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.personalmushaf.R;
import com.example.personalmushaf.model.Ayah;
import com.example.personalmushaf.model.AyahBounds;
import com.example.personalmushaf.model.Page;

import java.util.List;

public class QuranPageFragment extends Fragment {

    private SharedPreferences preferences;
    private int page_number;
    private View v;
    private Page page;
    private Ayah highlightedAyah = null;
    private boolean isHighlighted = false;
    private float x;
    private float y;

    public QuranPageFragment () {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_page, container, false);

        page_number = getArguments().getInt("page_number");

        page = new Page(page_number);

        preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());

        String mushaf = preferences.getString("mushaf", "madani_15_line");

        ImageView imageView = v.findViewById(R.id.page1);

        String path;

        if (mushaf.equals("madani_15_line"))
            path = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/15_line/pg_" + page_number + ".png";
        else
            path = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/13_line/13_pg_" + page_number + ".png";

        ImageUtils.getInstance().loadBitmap(path, imageView);

        if (mushaf.equals("madani_15_line"))
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
                Ayah ayah = page.getAyahFromCoordinates(imageView, x, y);
                List<AyahBounds> ayahBounds = ayah.getAyahBounds();


                Paint myPaint = new Paint();
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setColor(Color.WHITE);
                //myPaint.setAlpha(50);

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
