package com.example.personalmushaf.navigation.tabs.juzquartertab;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.personalmushaf.QuranActivity;
import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.QuranPageData;

import androidx.recyclerview.widget.RecyclerView;

public class JuzQuarterAdapter extends RecyclerView.Adapter<JuzQuarterAdapter.JuzViewHolder> {
    private String[] dataSet;
    private int juz;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class JuzViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RippleView rippleView;
        public JuzViewHolder(RippleView v) {
            super(v);
            rippleView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataSet)
    public JuzQuarterAdapter(String[] myDataset, int juz) {
        dataSet = myDataset;
        this.juz = juz;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public JuzViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);


        JuzViewHolder vh = new JuzViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final JuzViewHolder holder, final int position) {
        // - get element from your dataSet at this position
        // - replace the contents of the view with that element

        TextView textView = (TextView) holder.rippleView.getChildAt(0);
        textView.setText(dataSet[position]);
        textView.setTextDirection(View.TEXT_DIRECTION_LTR);

        alternateBackgroundColor(textView, position);

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


    // Return the size of your dataSet (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.length;
    }


    private void alternateBackgroundColor(TextView textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent));
    }


    private void juzContentProcedure(RippleView rippleView, int position) {
        TextView textView = (TextView) rippleView.getChildAt(0);

        final Intent goToJuz = new Intent(textView.getContext(), QuranActivity.class);

        goToJuz.putExtra("new page number", QuranPageData.getInstance().juzContentPageNumbers[juz-1][position]);

        goToJuz.putExtra("from", "NavigationActivity");

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToJuz);
                ((Activity) rippleView.getContext()).finishAffinity();
            }
        });
    }

}

