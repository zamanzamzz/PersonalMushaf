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

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

public class QuranPageRecyclerViewAdapter extends RecyclerView.Adapter<QuranPageRecyclerViewAdapter.QuranPageViewHolder> {
    Ayah highlightedAyah = null;
    boolean isHighlighted = false;
    SharedPreferences preferences;
    String mushafVersion;
    Page page;


    final int x1 = 98;
    final int x2 = 946;
    final int[] y1 = {115, 208, 301, 394, 487, 580, 673, 766, 859, 951,  1044, 1137, 1230};
    final int[] y1Touch = {111, 207, 301, 395, 487, 579, 672, 765, 859, 955, 1047, 1140, 1234};
    final int[] y2Touch = {207, 301, 395, 487, 579, 672, 765, 859, 955, 1047, 1140, 1234, 1327};
    final int[] y2 = {204, 297, 390, 483, 576, 669, 762, 855, 947, 1040, 1133, 1226, 1319};
    private float x;
    private float y;

    public static class QuranPageViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public QuranPageViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public QuranPageRecyclerViewAdapter(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        mushafVersion = preferences.getString("mushaf", "madani_15_line");
    }

    public void setPageData(Page page) {
        this.page = page;
    }

    @Override
    public QuranPageViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_page, parent, false);


        QuranPageViewHolder vh = new QuranPageViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(QuranPageViewHolder holder, int position) {

        final ImageView imageView = (ImageView) holder.linearLayout.getChildAt(0);

        final String path;

        if (mushafVersion.equals("madani_15_line"))
            path = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/15_line/pg_" + (position+1) + ".png";
        else
            path = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/13_line/13_pg_" + (position+1) + ".png";

        loadBitmap(path, imageView);

        setHighlight(imageView, path);
        /*final int id = imageView.getResources().getIdentifier("pg_" + QuranConstants.singlePageSets[position] + identifier
                , "drawable", imageView.getContext().getPackageName());

        imageView.setImageDrawable(imageView.getResources().getDrawable(id));*/
        //Glide.with(imageView.getContext()).load(BitmapFactory.decodeFile(path)).centerInside().into(imageView);
    }


    @Override
    public int getItemCount() {
        return mushafVersion.equals("madani_15_line") ? 604 : 848;
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

