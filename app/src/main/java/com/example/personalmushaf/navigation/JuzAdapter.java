package com.example.personalmushaf.navigation;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.personalmushaf.MainActivity;
import com.example.personalmushaf.R;

import androidx.recyclerview.widget.RecyclerView;

public class JuzAdapter extends RecyclerView.Adapter<JuzAdapter.MyViewHolder> {
    private String[] mDataset;
    private int type;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public JuzAdapter(String[] myDataset, int type) {
        mDataset = myDataset;
        this.type = type;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public JuzAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_juz, parent, false);


        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);
        if (position % 2 == 0)
            holder.textView.setBackgroundColor(holder.textView.getResources().getColor(R.color.colorPrimary));
        else
            holder.textView.setBackgroundColor(holder.textView.getResources().getColor(R.color.colorAccent));

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextView textView = (TextView) view;
                CharSequence text = ((TextView) view).getText();
                int juzNumber;

                String stringText = text.toString();

                juzNumber = Integer.valueOf(stringText.substring(0, 2));

                Intent goToJuz = new Intent(view.getContext(), MainActivity.class);

                goToJuz.putExtra("new page number", QuranPageData.getInstance().JuzPageNumbers[juzNumber-1]);


                view.getContext().startActivity(goToJuz);

                ((Activity) view.getContext()).finish();

                return true;
            }
        });

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                CharSequence text = ((TextView) view).getText();
                int juzNumber;

                String stringText = text.toString();

                juzNumber = Integer.valueOf(stringText.substring(0, 2));

                Intent goToJuzContent = new Intent(view.getContext(), NavigationActivity.class);

                goToJuzContent.putExtra("new page number", -1);

                goToJuzContent.putExtra("juzz number", juzNumber);


                view.getContext().startActivity(goToJuzContent);

                if (type == -1)
                    ((Activity) view.getContext()).finish();
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

