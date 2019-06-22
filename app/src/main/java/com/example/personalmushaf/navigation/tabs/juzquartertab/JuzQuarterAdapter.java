package com.example.personalmushaf.navigation.tabs.juzquartertab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.example.personalmushaf.QuranActivity;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.MadaniFifteenLinePageData;

import androidx.recyclerview.widget.RecyclerView;

public class JuzQuarterAdapter extends RecyclerView.Adapter<JuzQuarterAdapter.JuzViewHolder> {
    private String[][] dataSet;
    private int juzNumber;
    private SharedPreferences preferences;
    private String mushaf;

    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public JuzViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public JuzQuarterAdapter(String[][] myDataSet, int juzNumber) {
        dataSet = myDataSet;
        this.juzNumber = juzNumber;
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

        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(holder.rippleView.getContext());

        if (mushaf == null)
            mushaf = preferences.getString("mushaf", "madani_15_line");

        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        ImageView quarterImage = (ImageView) layout.getChildAt(0);

        if (mushaf.equals("madani_15_line")) {
            if (position <= 3)
                id = quarterImage.getResources().getIdentifier("quarter_" + (position+1),
                        "drawable", quarterImage.getContext().getPackageName());
            else
                id = quarterImage.getResources().getIdentifier("quarter_" + (position-3),
                        "drawable", quarterImage.getContext().getPackageName());
        } else
            id = quarterImage.getResources().getIdentifier("quarter_" + position,
                    "drawable", quarterImage.getContext().getPackageName());

        Glide.with(layout.getContext()).load(id).into(quarterImage);

        TextView quarterLength = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView quarterPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView quarterPrefix = (TextView) layout.getChildAt(2);

        String[] juzContentInfo = dataSet[position];

        String length;

        if (position != 0)
            length = juzContentInfo[0] + " pages";
        else
            length = "";

        if (!mushaf.equals("madani_15_line"))
            quarterLength.setText(length);

        quarterPageNumber.setText(juzContentInfo[1]);

        if (mushaf.equals("madani_15_line"))
            quarterPrefix.setText(MadaniFifteenLinePageData.juzQuarterPrefixes[juzNumber-1][position]);

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
        return dataSet.length;
    }


    private void alternateBackgroundColor(LinearLayout layout, int position) {
        if (position % 2 == 0)
            layout.setBackgroundColor(layout.getResources().getColor(R.color.colorPrimary));
        else
            layout.setBackgroundColor(layout.getResources().getColor(R.color.colorAccent));
    }


    private void juzContentProcedure(RippleView rippleView, int position) {
        final Intent goToJuz = new Intent(rippleView.getContext(), QuranActivity.class);

        goToJuz.putExtra("new page number", Integer.valueOf(dataSet[position][1]));

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToJuz);
            }
        });
    }

}

