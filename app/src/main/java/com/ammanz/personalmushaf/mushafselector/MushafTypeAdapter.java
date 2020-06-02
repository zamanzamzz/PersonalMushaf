package com.ammanz.personalmushaf.mushafselector;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ammanz.personalmushaf.R;

public class MushafTypeAdapter extends RecyclerView.Adapter<MushafTypeAdapter.MushafViewHolder> {
    private String[] mushafTypes = {"13 Lines", "15 Lines"};
    private boolean fromSettings;

    public static class MushafViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public MushafViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public MushafTypeAdapter(boolean fromSettings) {
        this.fromSettings = fromSettings;
    }

    @Override
    public MushafViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);


        MushafViewHolder vh = new MushafViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MushafViewHolder holder, final int position) {

        TextView text = (TextView) holder.linearLayout.getChildAt(0);

        text.setText(mushafTypes[position]);

        text.setBackgroundColor(424242);

        holder.linearLayout.setOnClickListener(v -> {
            Intent chooseMushafStyle = new Intent(v.getContext(), MushafStyleActivity.class);
            chooseMushafStyle.putExtra("mushaf_type", position);
            chooseMushafStyle.putExtra("from_settings", fromSettings);
            v.getContext().startActivity(chooseMushafStyle);
        });

    }

    @Override
    public int getItemCount() {
        return mushafTypes.length;
    }


}

