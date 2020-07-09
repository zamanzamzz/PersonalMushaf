package com.ammanz.personalmushaf.navigation.tabs.surahtab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ammanz.personalmushaf.QuranActivity;
import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {
    private int[][] surahInfo;
    private int[] surahPageNumbers;
    private String[] prefixes;
    private double[] surahLengthsInJuz;
    private QuranSettings quranSettings;

    public static class SurahViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public SurahViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public SurahAdapter(int[][] surahInfo, int[] surahPageNumbers, String[] prefixes, double[] surahLengthsInJuz) {
        this.surahInfo = surahInfo;
        this.surahPageNumbers = surahPageNumbers;
        this.prefixes = prefixes;
        this.surahLengthsInJuz = surahLengthsInJuz;
        quranSettings = QuranSettings.getInstance();
    }

    @Override
    public SurahViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_surah, parent, false);

        SurahViewHolder vh = new SurahViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final @NonNull SurahViewHolder holder, final int position) {
        LinearLayout layout = holder.linearLayout;

        TextView surahNumber = (TextView) layout.getChildAt(0);
        TextView surahLength = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView surahPageNumber = (TextView) ((LinearLayout) ((LinearLayout) layout.getChildAt(1)).getChildAt(1)).getChildAt(1);
        TextView surahOrigin = (TextView) ((LinearLayout) ((LinearLayout) layout.getChildAt(1)).getChildAt(1)).getChildAt(0);
        TextView surahStart = (TextView) layout.getChildAt(2);


        String origin = surahInfo[position][2] == 1 ? "مكي" : "مدني";

        final int pageNumber = surahPageNumbers[position];

        if (quranSettings.getSimplifyInterface(surahNumber.getContext())) {
            surahNumber.setText(Integer.toString(surahInfo[position][0]));
            surahNumber.setTextSize(20);
            surahStart.setText("Surah " + prefixes[position]);
            surahStart.setTextSize(18);
        } else {
            surahNumber.setText(layout.getResources().getStringArray(R.array.arabic_numerals)[surahInfo[position][0] - 1]);
            surahStart.setText("سورة " + prefixes[position]);
        }

        surahLength.setText(String.format("%.2f", surahLengthsInJuz[position]) + " pages");
        surahPageNumber.setText(Integer.toString(pageNumber));



        surahOrigin.setText(origin);

        alternateBackgroundColor(layout, position);

        holder.linearLayout.setOnClickListener(v -> {
            LinearLayout linearLayout = (LinearLayout) v;

            final Intent goToSurah = new Intent(linearLayout.getContext(), QuranActivity.class);

            goToSurah.putExtra("new page number", pageNumber);

            goToSurah.putExtra("from", "NavigationActivity");

            linearLayout.getContext().startActivity(goToSurah);
        });
    }

    @Override
    public int getItemCount() {
        return surahInfo.length;
    }

    private void alternateBackgroundColor(LinearLayout textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary, textView.getContext().getTheme()));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent, textView.getContext().getTheme()));
    }
}
