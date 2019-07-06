package com.example.personalmushaf.quranpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.personalmushaf.R;
import com.example.personalmushaf.model.Ayah;
import com.example.personalmushaf.model.AyahBounds;
import com.example.personalmushaf.model.Page;
import com.example.personalmushaf.navigation.QuranConstants;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

public class QuranDualPageRecyclerViewAdapter extends RecyclerView.Adapter<QuranDualPageRecyclerViewAdapter.QuranPageDualViewHolder> {

    private int[] highlightedLine = {0, 0};
    private boolean isHighlighted = false;
    private SharedPreferences preferences;
    private String currentMushaf;
    private Page rightPage;
    private Page leftPage;
    private Ayah highlightedAyah;


    private int[][] dataSet;
    private final int x1 = 98;
    private final int x2 = 946;
    private final int[] y1 = {115, 208, 301, 394, 487, 580, 673, 766, 859, 951,  1044, 1137, 1230};
    private final int[] y1Touch = {111, 207, 301, 395, 487, 579, 672, 765, 859, 955, 1047, 1140, 1234};
    private final int[] y2Touch = {207, 301, 395, 487, 579, 672, 765, 859, 955, 1047, 1140, 1234, 1327};
    private final int[] y2 = {204, 297, 390, 483, 576, 669, 762, 855, 947, 1040, 1133, 1226, 1319};
    private float x;
    private float y;

    public static class QuranPageDualViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public QuranPageDualViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public QuranDualPageRecyclerViewAdapter(int[][] dataSet, Context context) {
        this.dataSet = dataSet;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        currentMushaf = preferences.getString("mushaf", "madani_15_line");
    }

    @Override
    public QuranPageDualViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_page, parent, false);


        QuranPageDualViewHolder vh = new QuranPageDualViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuranPageDualViewHolder holder, int position) {



        final String rightPagePath;
        final String leftPagePath;

        if (!currentMushaf.equals("madani_15_line")) {
            if (position != 0 && position != 424) {
                final ImageView rightImage = (ImageView) holder.linearLayout.getChildAt(1);
                final ImageView leftImage = (ImageView) holder.linearLayout.getChildAt(0);


                rightPagePath = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/13_line/13_pg_" + QuranConstants.naskh13LineDualPageSets[position][0] + ".png";
                leftPagePath = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/13_line/13_pg_" + QuranConstants.naskh13LineDualPageSets[position][1] + ".png";

                loadBitmap(rightPagePath, rightImage);
                loadBitmap(leftPagePath, leftImage);

                setHighlightDual(leftImage, rightImage, leftPagePath, rightPagePath, false);
                setHighlightDual(rightImage, leftImage, rightPagePath, leftPagePath, true);

            } else {
                final ImageView image;
                boolean isRight;
                if (position == 424) {
                    image = (ImageView) holder.linearLayout.getChildAt(1);
                    isRight = true;
                }
                else {
                    image = (ImageView) holder.linearLayout.getChildAt(0);
                    isRight = false;
                }

                rightPagePath = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/13_line/13_pg_" + QuranConstants.naskh13LineDualPageSets[position][0] + ".png";

                loadBitmap(rightPagePath, image);

                setHighlightSingle(image, rightPagePath, isRight);
            }

        }
        else {

            if (position < 302 && position >= 0) {
                leftPagePath = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/15_line/pg_" + QuranConstants.madani15LineDualPageSets[position][1] + ".png";
                rightPagePath = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/15_line/pg_" + QuranConstants.madani15LineDualPageSets[position][0] + ".png";

                final ImageView leftImage = (ImageView) holder.linearLayout.getChildAt(0);
                final ImageView rightImage = (ImageView) holder.linearLayout.getChildAt(1);

                loadBitmap(leftPagePath, leftImage);
                loadBitmap(rightPagePath, rightImage);

                setHighlightDual(leftImage, rightImage, leftPagePath, rightPagePath, false);
                setHighlightDual(rightImage, leftImage, rightPagePath, leftPagePath, true);
            }


        }
    }


    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public void setPages(int dualPageNumber) {

        if (currentMushaf.equals("madani_15_line")) {
            rightPage = new Page(QuranConstants.madani15LineDualPageSets[dualPageNumber][0]);
            leftPage = new Page(QuranConstants.madani15LineDualPageSets[dualPageNumber][1]);
        } else {
            if (dualPageNumber == 0) {
                rightPage = null;
                leftPage = new Page(QuranConstants.naskh13LineDualPageSets[dualPageNumber][0]);
            } else if (dualPageNumber == 848) {
                rightPage = new Page(QuranConstants.naskh13LineDualPageSets[dualPageNumber][0]);
                leftPage = null;
            } else {
                rightPage = new Page(QuranConstants.naskh13LineDualPageSets[dualPageNumber][0]);
                leftPage = new Page(QuranConstants.naskh13LineDualPageSets[dualPageNumber][1]);
            }
        }
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
                    ayah = rightPage.getAyahFromCoordinates(imageView, x, y);
                else
                    ayah = leftPage.getAyahFromCoordinates(imageView, x, y);

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
                    ayah = rightPage.getAyahFromCoordinates(imageView, x, y);
                else
                    ayah = leftPage.getAyahFromCoordinates(imageView, x, y);

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

    private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private String path = "";

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            path = params[0];
            return BitmapFactory.decodeFile(path);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public void loadBitmap(String path, ImageView imageView) {
        if (cancelPotentialWork(path, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(imageView.getResources(), null, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(path);
        }
    }

    private static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public static boolean cancelPotentialWork(String path, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapPath = bitmapWorkerTask.path;
            if (!bitmapPath.equals(path)) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }


}
