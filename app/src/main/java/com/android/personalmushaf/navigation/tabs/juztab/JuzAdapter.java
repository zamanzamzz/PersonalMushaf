package com.android.personalmushaf.navigation.tabs.juztab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andexert.library.RippleView;
import com.android.personalmushaf.QuranActivity;
import com.android.personalmushaf.R;
import com.android.personalmushaf.navigation.NavigationActivity;

public class JuzAdapter extends RecyclerView.Adapter<JuzAdapter.JuzViewHolder> {
    private int[] juzPageNumbers;
    private int[] juzLengths;
    private String[] juzNames;

    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public JuzViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public JuzAdapter(String[] juzNames, int[] juzPageNumbers, int[] juzLengths) {
        this.juzNames = juzNames;
        this.juzPageNumbers = juzPageNumbers;
        this.juzLengths = juzLengths;
    }

    @Override
    public JuzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_juz, parent, false);


        JuzViewHolder vh = new JuzViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final JuzViewHolder holder, final int position) {

        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        TextView juz =  (TextView) layout.getChildAt(0);
        TextView juzPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView juzLength = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView juzStart = (TextView) layout.getChildAt(2);

        String length = juzLengths[position] + " pages";

        juz.setText(layout.getResources().getStringArray(R.array.arabic_numerals)[position]);
        juzLength.setText(length);
        juzStart.setText(juzNames[position]);
        juzPageNumber.setText(Integer.toString(juzPageNumbers[position]));

        alternateBackgroundColor(layout, position);

        holder.rippleView.setOnClickListener(new RippleView.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);
                juzToJuzContentProcedure(rippleView, position + 1);

            }
        });

        holder.rippleView.setOnLongClickListener(new RippleView.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);

                return juzToPage(rippleView, position);
            }
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

    private void juzToJuzContentProcedure(RippleView rippleView, int juzNumber) {
        final Intent goToJuzContent = new Intent(rippleView.getContext(), NavigationActivity.class);

        goToJuzContent.putExtra("juz number", juzNumber);

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToJuzContent);
            }
        });
    }



    private boolean juzToPage(RippleView rippleView, int juzNumber) {
        final Intent goToJuz = new Intent(rippleView.getContext(), QuranActivity.class);

        goToJuz.putExtra("new page number", Integer.valueOf(juzPageNumbers[juzNumber]));

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToJuz);
            }
        });
        return true;
    }

}

