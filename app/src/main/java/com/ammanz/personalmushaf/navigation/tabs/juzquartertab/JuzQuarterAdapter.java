package com.ammanz.personalmushaf.navigation.tabs.juzquartertab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ammanz.personalmushaf.QuranActivity;
import com.ammanz.personalmushaf.R;
import com.andexert.library.RippleView;

public class JuzQuarterAdapter extends RecyclerView.Adapter<JuzQuarterAdapter.JuzViewHolder> {
    private int[][] quarterInfo;
    private int[] quarterPageNumbers;
    private double[] lengths;
    private String[] prefixes;

    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public JuzViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public JuzQuarterAdapter(int[][] quarterInfo, int[] quarterPageNumbers, String[] prefixes, double[] lengths) {
        this.quarterInfo = quarterInfo;
        this.quarterPageNumbers = quarterPageNumbers;
        this.prefixes = prefixes;
        this.lengths = lengths;
    }

    @Override
    public JuzViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_juz_quarter, parent, false);

        JuzViewHolder vh = new JuzViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final JuzViewHolder holder, final int position) {
        int id;

        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        ImageView quarterImage = (ImageView) layout.getChildAt(0);

        if (getItemCount() == 8) {
            if (position <= 3)
                id = quarterImage.getResources().getIdentifier("quarter_" + (position+1),
                        "drawable", quarterImage.getContext().getPackageName());
            else
                id = quarterImage.getResources().getIdentifier("quarter_" + (position-3),
                        "drawable", quarterImage.getContext().getPackageName());
        } else
            id = quarterImage.getResources().getIdentifier("quarter_" + position,
                    "drawable", quarterImage.getContext().getPackageName());

        quarterImage.setImageDrawable(quarterImage.getResources().getDrawable(id, null));

        TextView quarterLength = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView quarterPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView quarterPrefix = (TextView) layout.getChildAt(2);

        quarterLength.setText(String.format("%.2f", lengths[position])+ " pages");

        quarterPageNumber.setText(quarterPageNumbers[position] + "\t\t\t\t" + quarterInfo[position][1] + ":" + quarterInfo[position][2]);
        quarterPrefix.setText(prefixes[position]);



        alternateBackgroundColor(layout, position);

        holder.rippleView.setOnClickListener(new RippleView.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);
                juzContentProcedure(rippleView, position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return quarterInfo.length;
    }


    private void alternateBackgroundColor(LinearLayout textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary, textView.getContext().getTheme()));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent, textView.getContext().getTheme()));
    }


    private void juzContentProcedure(RippleView rippleView, int position) {
        final Intent goToJuz = new Intent(rippleView.getContext(), QuranActivity.class);

        goToJuz.putExtra("surah", quarterInfo[position][1]);
        goToJuz.putExtra("ayah", quarterInfo[position][2]);
        goToJuz.putExtra("new page number", quarterPageNumbers[position]);

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToJuz);
            }
        });
    }

}

