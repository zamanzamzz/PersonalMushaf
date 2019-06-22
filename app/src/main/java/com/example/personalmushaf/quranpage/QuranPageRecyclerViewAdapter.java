package com.example.personalmushaf.quranpage;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
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
import com.example.personalmushaf.navigation.MadaniFifteenLinePageData;
import com.example.personalmushaf.navigation.NaskhThirteenLinePageData;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class QuranPageRecyclerViewAdapter extends RecyclerView.Adapter<QuranPageRecyclerViewAdapter.ThirteenLineViewHolder> {

    private int[] dataSet;

    int highlightedLine = 0;
    boolean isHighlighted = false;
    SharedPreferences preferences;


    final int x1 = 98;
    final int x2 = 946;
    final int[] y1 = {115, 208, 301, 394, 487, 580, 673, 766, 859, 951,  1044, 1137, 1230};
    final int[] y1Touch = {111, 207, 301, 395, 487, 579, 672, 765, 859, 955, 1047, 1140, 1234};
    final int[] y2Touch = {207, 301, 395, 487, 579, 672, 765, 859, 955, 1047, 1140, 1234, 1327};
    final int[] y2 = {204, 297, 390, 483, 576, 669, 762, 855, 947, 1040, 1133, 1226, 1319};
    private float x;
    private float y;

    public static class ThirteenLineViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public ThirteenLineViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public QuranPageRecyclerViewAdapter(int[] dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ThirteenLineViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_page, parent, false);


        ThirteenLineViewHolder vh = new ThirteenLineViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ThirteenLineViewHolder holder, int position) {

        final ImageView imageView = (ImageView) holder.linearLayout.getChildAt(0);

        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(imageView.getContext());
        }

        String mushafVersion = preferences.getString("mushaf", "madani_15_line");

        final String path;

        if (mushafVersion.equals("madani_15_line"))
            path = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/15_line/pg_" + MadaniFifteenLinePageData.singlePageSets[position] + ".png";
        else
            path = Environment.getExternalStorageDirectory().getPath() + "/personal_mushaf/13_line/13_pg_" + NaskhThirteenLinePageData.singlePageSets[position] + ".png";

        loadBitmap(path, imageView);

        /*final int id = imageView.getResources().getIdentifier("pg_" + NaskhThirteenLinePageData.singlePageSets[position] + identifier
                , "drawable", imageView.getContext().getPackageName());

        imageView.setImageDrawable(imageView.getResources().getDrawable(id));*/
        //Glide.with(imageView.getContext()).load(BitmapFactory.decodeFile(path)).centerInside().into(imageView);
        //setHighlight(imageView, id, holder);
    }


    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setHighlight(final ImageView imageView, final String path, final ThirteenLineViewHolder holder) {
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
                Bitmap myBitmap = BitmapFactory.decodeResource(imageView.getResources(), path);
                int pageNumber = holder.getAdapterPosition() + 1;

                int i;

                for (i = 0; i < 13; i++) {
                    if (y >= y1Touch[i] && y <= y2Touch[i] && x >= x1 && x <= x2)
                        break;
                }

                if (i == 13)
                    return false;


                Paint myPaint = new Paint();
                myPaint.setStyle(Paint.Style.FILL);
                myPaint.setColor(Color.BLUE);
                myPaint.setAlpha(50);

                Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
                Canvas tempCanvas = new Canvas(tempBitmap);


                //Draw the image bitmap into the canvas
                tempCanvas.drawBitmap(myBitmap, 0, 0, null);

                if (pageNumber == 1) {
                    tempCanvas.drawRoundRect(new RectF(410, 248,854,333), 2, 2, myPaint);
                } else if (!isHighlighted || highlightedLine != i+1) {
                    tempCanvas.drawRoundRect(new RectF(x1,y1[i],x2,y2[i]), 2, 2, myPaint);
                    highlightedLine = i+1;
                    isHighlighted = true;
                } else
                    isHighlighted = false;


                imageView.setImageDrawable(new BitmapDrawable(imageView.getResources(), tempBitmap));

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

