package com.example.personalmushaf.navigation.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.personalmushaf.QuranActivity;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.QuranPageData;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {
    private String[] dataSet;

    public static class SurahViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RippleView rippleView;
        public SurahViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataSet)
    public SurahAdapter(String[] myDataset) {
        dataSet = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SurahViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);


        SurahViewHolder vh = new SurahViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final @NonNull SurahViewHolder holder, final int position) {
        final TextView textView = (TextView) holder.rippleView.getChildAt(0);
        textView.setText(dataSet[position]);
        textView.setTextDirection(View.TEXT_DIRECTION_LTR);

        alternateBackgroundColor(textView, position);

        holder.rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RippleView rippleView = (RippleView) v;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);

                int pageNumber = QuranPageData.getInstance().surahPageNumbers[position];

                final Intent goToSurah = new Intent(textView.getContext(), QuranActivity.class);

                goToSurah.putExtra("new page number", pageNumber);

                goToSurah.putExtra("from", "NavigationActivity");

                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        rippleView.getContext().startActivity(goToSurah);
                        ((Activity) rippleView.getContext()).finishAffinity();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    private void alternateBackgroundColor(TextView textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent));
    }
}
