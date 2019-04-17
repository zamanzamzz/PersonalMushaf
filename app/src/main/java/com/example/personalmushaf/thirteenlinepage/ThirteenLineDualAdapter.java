package com.example.personalmushaf.thirteenlinepage;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.ThirteenLinePageData;

import androidx.recyclerview.widget.RecyclerView;

public class ThirteenLineDualAdapter extends RecyclerView.Adapter<ThirteenLineDualAdapter.ThirteenLineDualViewHolder> {

    private int[][] dataSet;

    public static class ThirteenLineDualViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout linearLayout;
        public ThirteenLineDualViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public ThirteenLineDualAdapter(int[][] dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ThirteenLineDualViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_page, parent, false);


        ThirteenLineDualViewHolder vh = new ThirteenLineDualViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ThirteenLineDualViewHolder holder, int position) {
        if (position != 0 && position != 424) {
            ImageView imageView = (ImageView) holder.linearLayout.getChildAt(0);

            int id1 = imageView.getResources().getIdentifier("pg_" + ThirteenLinePageData.getInstance().dualPageSets[position][0]
                        , "drawable", imageView.getContext().getPackageName());

            int id2 = imageView.getResources().getIdentifier("pg_" + ThirteenLinePageData.getInstance().dualPageSets[position][1]
                    , "drawable", imageView.getContext().getPackageName());

            Glide.with(imageView.getContext()).load(id2).centerInside().into(imageView);

            imageView = (ImageView) holder.linearLayout.getChildAt(1);

            Glide.with(imageView.getContext()).load(id1).centerInside().into(imageView);
        } else {
            ImageView imageView;
            if (position == 424)
                imageView = (ImageView) holder.linearLayout.getChildAt(1);
            else
                imageView = (ImageView) holder.linearLayout.getChildAt(0);

            int id1 = imageView.getResources().getIdentifier("pg_" + ThirteenLinePageData.getInstance().dualPageSets[position][0]
                    , "drawable", imageView.getContext().getPackageName());

            Glide.with(imageView.getContext()).load(id1).centerInside().into(imageView);
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.length;
    }


}
