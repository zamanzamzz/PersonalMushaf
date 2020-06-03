package com.ammanz.personalmushaf.navigation.tabs.rukucontenttab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ammanz.personalmushaf.QuranActivity;
import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;

public class RukuContentAdapter extends RecyclerView.Adapter<RukuContentAdapter.JuzViewHolder> {
    private int[][] rukuInfo;
    private int[] rukuPageNumbers;
    private double[] rukuLengths;
    private String[] prefixes;

    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public JuzViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public RukuContentAdapter(int[][] rukuInfo, int[] rukuPageNumbers, double[] rukuLengths, String[] prefixes) {
        this.rukuInfo = rukuInfo;
        this.rukuPageNumbers = rukuPageNumbers;
        this.rukuLengths = rukuLengths;
        this.prefixes = prefixes;
    }

    @Override
    public JuzViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ruku_content, parent, false);


        JuzViewHolder vh = new JuzViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final JuzViewHolder holder, final int position) {

        LinearLayout layout = holder.linearLayout;

        TextView rukuNumber = (TextView) layout.getChildAt(0);
        TextView rukuPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView rukuLength = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView rukuPrefix = (TextView) layout.getChildAt(2);

        if (QuranSettings.getInstance().getSimplifyInterface(rukuNumber.getContext())) {
            rukuNumber.setText(Integer.toString(position + 1));
            rukuNumber.setTextSize(20);
        } else
            rukuNumber.setText(layout.getResources().getStringArray(R.array.arabic_numerals)[position]);

        rukuLength.setText(String.format("%.2f", rukuLengths[position]) + " pages");
        rukuPageNumber.setText(rukuPageNumbers[position] + "\t\t\t\t" + rukuInfo[position][0] + ":" + rukuInfo[position][1]);
        rukuPrefix.setText(prefixes[position]);

        alternateBackgroundColor(layout, position);

        holder.linearLayout.setOnClickListener(view -> {
            LinearLayout linearLayout = (LinearLayout) view;
            rukuContentToPage(linearLayout, position);
        });

    }

    @Override
    public int getItemCount() {
        return rukuInfo.length;
    }


    private void alternateBackgroundColor(LinearLayout textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary, textView.getContext().getTheme()));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent, textView.getContext().getTheme()));
    }



    private void rukuContentToPage(LinearLayout linearLayout, int selectedRuku) {
        final Intent goToRukuAyah = new Intent(linearLayout.getContext(), QuranActivity.class);

        goToRukuAyah.putExtra("from", "NavigationActivity");

        int pageNumber = Integer.valueOf(rukuPageNumbers[selectedRuku]);

        goToRukuAyah.putExtra("surah", rukuInfo[selectedRuku][0]);
        goToRukuAyah.putExtra("ayah", rukuInfo[selectedRuku][1]);
        goToRukuAyah.putExtra("new page number", pageNumber);

        linearLayout.getContext().startActivity(goToRukuAyah);
    }

}

