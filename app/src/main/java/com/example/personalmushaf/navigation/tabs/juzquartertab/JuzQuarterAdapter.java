package com.example.personalmushaf.navigation.tabs.juzquartertab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.example.personalmushaf.QuranActivity;
import com.example.personalmushaf.QuranSettings;
import com.example.personalmushaf.R;

public class JuzQuarterAdapter extends RecyclerView.Adapter<JuzQuarterAdapter.JuzViewHolder> {
    private String[][] dataSet;
    private String mushafVersion;

    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public JuzViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public JuzQuarterAdapter(String[][] myDataSet) {
        dataSet = myDataSet;
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

        mushafVersion = QuranSettings.getInstance().getMushafVersion(holder.rippleView.getContext());

        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        ImageView quarterImage = (ImageView) layout.getChildAt(0);

        if (mushafVersion.equals("madani_15_line")) {
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

        if (!mushafVersion.equals("madani_15_line"))
            quarterLength.setText(juzContentInfo[4] + " pages");

        quarterPageNumber.setText(juzContentInfo[0] + "\t\t\t\t" + juzContentInfo[1] + ":" + juzContentInfo[2]);
        quarterPrefix.setText(juzContentInfo[3]);


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


    private void alternateBackgroundColor(LinearLayout textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary, textView.getContext().getTheme()));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent, textView.getContext().getTheme()));
    }


    private void juzContentProcedure(RippleView rippleView, int position) {
        final Intent goToJuz = new Intent(rippleView.getContext(), QuranActivity.class);


        goToJuz.putExtra("new page number", Integer.valueOf(dataSet[position][0]));

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToJuz);
            }
        });
    }

}

