package com.example.personalmushaf.navigation.tabs.rukucontenttab;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.personalmushaf.QuranActivity;
import com.example.personalmushaf.R;

import androidx.recyclerview.widget.RecyclerView;

public class RukuContentAdapter extends RecyclerView.Adapter<RukuContentAdapter.JuzViewHolder> {
    private String[][] dataSet;

    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        public RippleView rippleView;
        public JuzViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    public RukuContentAdapter(String[][] myDataSet) {
        dataSet = myDataSet;
    }

    @Override
    public JuzViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ruku_content, parent, false);


        JuzViewHolder vh = new JuzViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final JuzViewHolder holder, final int position) {

        LinearLayout layout = (LinearLayout) holder.rippleView.getChildAt(0);

        TextView rukuNumber = (TextView) layout.getChildAt(0);
        TextView rukuPageNumber = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(1);
        TextView ayahRange = (TextView) ((LinearLayout) layout.getChildAt(1)).getChildAt(0);
        TextView rukuType = (TextView) layout.getChildAt(2);

        String[] rukuInfo = dataSet[position];

        String range;

        if (!rukuInfo[2].equals("") && !rukuInfo[3].equals(""))
            range = rukuInfo[1] + ":" + rukuInfo[2] + " - " + rukuInfo[1] + ":" + rukuInfo[3];
        else if (rukuInfo[2].equals(""))
            range = rukuInfo[1] + ":" + rukuInfo[3];
        else
            range = rukuInfo[1] + ":" + rukuInfo[2];

        rukuNumber.setText(rukuInfo[0]);
        ayahRange.setText(range);
        rukuPageNumber.setText(rukuInfo[4]);
        rukuType.setText(rukuInfo[5]);

        alternateBackgroundColor(layout, position);

        holder.rippleView.setOnClickListener(new RippleView.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);
                rukuContentToPage(rippleView, position);
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



    private void rukuContentToPage(RippleView rippleView, int selectedRuku) {
        final Intent goToRukuAyah = new Intent(rippleView.getContext(), QuranActivity.class);

        goToRukuAyah.putExtra("from", "NavigationActivity");

        int pageNumber = Integer.valueOf(dataSet[selectedRuku][4]);

        goToRukuAyah.putExtra("new page number", pageNumber);

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToRukuAyah);
            }
        });
    }

}

