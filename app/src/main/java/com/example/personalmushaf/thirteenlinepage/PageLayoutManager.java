package com.example.personalmushaf.thirteenlinepage;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PageLayoutManager extends LinearLayoutManager {
    public PageLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        RecyclerView.State state = new RecyclerView.State();
        getExtraLayoutSpace(state);
    }

    @Override
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        super.getExtraLayoutSpace(state);
        return this.getWidth()/10;
    }
}
