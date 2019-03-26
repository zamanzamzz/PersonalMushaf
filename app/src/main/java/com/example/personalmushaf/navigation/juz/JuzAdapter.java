package com.example.personalmushaf.navigation.juz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.ruku.RukuViewHolder;
import com.example.personalmushaf.navigation.ruku.Ruku;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class JuzAdapter extends ExpandableRecyclerViewAdapter<JuzViewHolder, RukuViewHolder> {

    public JuzAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public JuzViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_juz, parent, false);
        return new JuzViewHolder(view);
    }

    @Override
    public RukuViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ruku, parent, false);
        return new RukuViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(RukuViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {

        final Ruku ruku = ((Juz) group).getItems().get(childIndex);
        holder.setRukuName(ruku.getName());
    }

    @Override
    public void onBindGroupViewHolder(JuzViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {

        holder.setJuzTitle(group);
    }
}
