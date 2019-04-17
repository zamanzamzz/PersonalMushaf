package com.example.personalmushaf.navigation.tabs.surahtab;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.personalmushaf.QuranActivity;
import com.example.personalmushaf.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {
    private String[][] dataSet;
    int juzNumber;

    public static class SurahViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RippleView rippleView;
        public SurahViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataSet)
    public SurahAdapter(String[][] myDataset, int juzNumber) {
        dataSet = myDataset;
        this.juzNumber = juzNumber;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SurahViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_surah, parent, false);


        SurahViewHolder vh = new SurahViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final @NonNull SurahViewHolder holder, final int position) {
        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        TextView surahNumber = (TextView) layout.getChildAt(0);
        TextView surahPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView surahOrigin = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView surahStart = (TextView) layout.getChildAt(2);

        final String[] surahInfo = dataSet[position];

        surahStart.setText(surahInfo[0]);
        surahPageNumber.setText(surahInfo[1]);
        surahNumber.setText(surahInfo[2]);
        surahOrigin.setText(surahInfo[3]);

        alternateBackgroundColor(layout, position);

        holder.rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RippleView rippleView = (RippleView) v;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);

                int pageNumber = Integer.valueOf(surahInfo[1]);

                final Intent goToSurah = new Intent(rippleView.getContext(), QuranActivity.class);

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

    private void alternateBackgroundColor(LinearLayout layout, int position) {
        if (position % 2 == 0)
            layout.setBackgroundColor(layout.getResources().getColor(R.color.colorPrimary));
        else
            layout.setBackgroundColor(layout.getResources().getColor(R.color.colorAccent));
    }
}
