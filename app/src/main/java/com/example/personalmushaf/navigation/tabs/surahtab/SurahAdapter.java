package com.example.personalmushaf.navigation.tabs.surahtab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andexert.library.RippleView;
import com.example.personalmushaf.QuranActivity;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.QuranConstants;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {
    private String[][] dataSet;
    int juzNumber;
    private SharedPreferences preferences;


    public static class SurahViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public SurahViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public SurahAdapter(String[][] myDataSet, int juzNumber) {
        dataSet = myDataSet;
        this.juzNumber = juzNumber;
    }

    @Override
    public SurahViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_surah, parent, false);

        preferences = PreferenceManager.getDefaultSharedPreferences(parent.getContext());

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

        String origin = Integer.valueOf(surahInfo[2]) == 1 ? "مكي" : "مدني";

        final int pageNumber = preferences.getString("mushaf", "madani_15_line").equals("madani_15_line") ?
                                Integer.valueOf(surahInfo[0]): Integer.valueOf(surahInfo[1]);

        surahStart.setText(surahInfo[3]);
        surahPageNumber.setText(Integer.toString(pageNumber));
        surahNumber.setText(QuranConstants.arabicNumeralsThreeDigits[position]);
        surahOrigin.setText(origin);

        alternateBackgroundColor(layout, position);

        holder.rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RippleView rippleView = (RippleView) v;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);


                final Intent goToSurah = new Intent(rippleView.getContext(), QuranActivity.class);

                goToSurah.putExtra("new page number", pageNumber);

                goToSurah.putExtra("from", "NavigationActivity");

                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        rippleView.getContext().startActivity(goToSurah);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    private void alternateBackgroundColor(LinearLayout textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary, textView.getContext().getTheme()));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent, textView.getContext().getTheme()));
    }
}
