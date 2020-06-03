package com.ammanz.personalmushaf.navigation.tabs.juztab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ammanz.personalmushaf.QuranActivity;
import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.navigation.NavigationActivity;

public class JuzAdapter extends RecyclerView.Adapter<JuzAdapter.JuzViewHolder> {
    private int[] juzPageNumbers;
    private double[] juzLengths;
    private String[] juzNames;

    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout linearLayout;
        public JuzViewHolder(LinearLayout v) {
            super(v);
            linearLayout = v;
        }
    }

    public JuzAdapter(String[] juzNames, int[] juzPageNumbers, double[] juzLengths) {
        this.juzNames = juzNames;
        this.juzPageNumbers = juzPageNumbers;
        this.juzLengths = juzLengths;
    }

    @Override
    public JuzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_juz, parent, false);


        JuzViewHolder vh = new JuzViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final JuzViewHolder holder, final int position) {

        LinearLayout layout = holder.linearLayout;

        TextView juz =  (TextView) layout.getChildAt(0);
        TextView juzPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView juzLength = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView juzStart = (TextView) layout.getChildAt(2);

        String length = String.format("%.2f", juzLengths[position]) + " pages";

        if (QuranSettings.getInstance().getSimplifyInterface(juz.getContext())) {
            juz.setText(Integer.toString(position + 1));
            juz.setTextSize(20);
        } else
            juz.setText(layout.getResources().getStringArray(R.array.arabic_numerals)[position]);
        juzLength.setText(length);
        juzStart.setText(juzNames[position]);
        juzPageNumber.setText(Integer.toString(juzPageNumbers[position]));

        alternateBackgroundColor(layout, position);

        holder.linearLayout.setOnClickListener(view -> {
            LinearLayout linearLayout = (LinearLayout) view;
            juzToJuzContentProcedure(linearLayout, position + 1);

        });

        holder.linearLayout.setOnLongClickListener(view -> {
            LinearLayout linearLayout = (LinearLayout) view;

            return juzToPage(linearLayout, position);
        });

    }

    @Override
    public int getItemCount() {
        return juzPageNumbers.length;
    }


    private void alternateBackgroundColor(LinearLayout textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary, textView.getContext().getTheme()));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent, textView.getContext().getTheme()));
    }

    private void juzToJuzContentProcedure(LinearLayout linearLayout, int juzNumber) {
        final Intent goToJuzContent = new Intent(linearLayout.getContext(), NavigationActivity.class);

        goToJuzContent.putExtra("juz number", juzNumber);

        linearLayout.getContext().startActivity(goToJuzContent);
    }



    private boolean juzToPage(LinearLayout linearLayout, int juzNumber) {
        final Intent goToJuz = new Intent(linearLayout.getContext(), QuranActivity.class);

        goToJuz.putExtra("new page number", Integer.valueOf(juzPageNumbers[juzNumber]));

        linearLayout.getContext().startActivity(goToJuz);

        return true;
    }

}

