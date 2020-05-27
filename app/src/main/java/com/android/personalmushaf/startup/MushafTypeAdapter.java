package com.android.personalmushaf.startup;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andexert.library.RippleView;
import com.android.personalmushaf.R;

public class MushafTypeAdapter extends RecyclerView.Adapter<MushafTypeAdapter.MushafViewHolder> {
    private String[] mushafTypes = {"13 Lines", "15 Lines"};

    public static class MushafViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public MushafViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public MushafTypeAdapter() {

    }

    @Override
    public MushafViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);


        MushafViewHolder vh = new MushafViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MushafViewHolder holder, final int position) {

        TextView text = (TextView) holder.rippleView.getChildAt(0);

        text.setText(mushafTypes[position]);

        text.setBackgroundColor(424242);

        holder.rippleView.setOnClickListener(v -> {
            RippleView rippleView = (RippleView) v;
            rippleView.setRippleDuration(75);
            rippleView.setFrameRate(10);
            ((RippleView) v).setOnRippleCompleteListener((r -> {
                Intent chooseMushafStyle = new Intent(r.getContext(), StartupMushafStyleActivity.class);
                chooseMushafStyle.putExtra("mushaf_type", position);
                r.getContext().startActivity(chooseMushafStyle);
            }));
        });

    }

    @Override
    public int getItemCount() {
        return mushafTypes.length;
    }


}

