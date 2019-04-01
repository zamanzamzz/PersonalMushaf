package com.example.personalmushaf.thirteenlinepage;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bumptech.glide.Glide;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.QuranPageData;

import androidx.recyclerview.widget.RecyclerView;

public class ThirteenLineAdapter extends RecyclerView.Adapter<ThirteenLineAdapter.ThirteenLineViewHolder> {

    private int[] dataSet;

    public static class ThirteenLineViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout linearLayout;
        public ThirteenLineViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public ThirteenLineAdapter(int[] dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ThirteenLineViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_page, parent, false);


        ThirteenLineViewHolder vh = new ThirteenLineViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ThirteenLineViewHolder holder, int position) {
        ImageView imageView = (ImageView) holder.linearLayout.getChildAt(0);

        int id = imageView.getResources().getIdentifier("pg_" + QuranPageData.getInstance().singlePageSets[position]
                , "drawable", imageView.getContext().getPackageName());

        Glide.with(imageView.getContext()).load(id).centerInside().into(imageView);
    }


    @Override
    public int getItemCount() {
        return dataSet.length;
    }


}

