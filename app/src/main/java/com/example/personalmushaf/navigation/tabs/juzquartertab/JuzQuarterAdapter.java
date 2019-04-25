package com.example.personalmushaf.navigation.tabs.juzquartertab;

import android.content.Intent;
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
import com.example.personalmushaf.navigation.ThirteenLinePageData;

import androidx.recyclerview.widget.RecyclerView;

public class JuzQuarterAdapter extends RecyclerView.Adapter<JuzQuarterAdapter.JuzViewHolder> {
    private String[][] dataSet;

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

        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        ImageView quarterImage = (ImageView) layout.getChildAt(0);

        int id = quarterImage.getResources().getIdentifier("quarter_" + position,
                    "drawable", quarterImage.getContext().getPackageName());

        Glide.with(layout.getContext()).load(id).into(quarterImage);

        TextView quarterLength = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView quarterPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView quarterType = (TextView) layout.getChildAt(2);

        String[] juzContentInfo = dataSet[position];

        String length;

        if (position != 0)
            length = juzContentInfo[0] + " pages";
        else
            length = "";

        quarterLength.setText(length);
        quarterPageNumber.setText(juzContentInfo[1]);
        quarterType.setText(ThirteenLinePageData.juzQuarterType[position]);


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

