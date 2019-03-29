package com.example.personalmushaf.navigation;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.example.personalmushaf.MainActivity;
import com.example.personalmushaf.R;

import androidx.recyclerview.widget.RecyclerView;

public class JuzAdapter extends RecyclerView.Adapter<JuzAdapter.JuzViewHolder> {
    private String[] mDataset;
    private int type;
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

    // Provide a suitable constructor (depends on the kind of dataset)
    public JuzAdapter(String[] myDataset, int type, int juz) {
        mDataset = myDataset;
        this.type = type;
        this.juz = juz;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public JuzViewHolder onCreateViewHolder(ViewGroup parent,
                                            int viewType) {
        // create a new view
        RippleView v = (RippleView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_juz, parent, false);


        JuzViewHolder vh = new JuzViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final JuzViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        TextView textView = (TextView) holder.rippleView.getChildAt(0);
        textView.setText(mDataset[position]);

        textView.setTextDirection(View.TEXT_DIRECTION_LTR);

        if (type == 1 && position > 1) {
            String textString = (QuranPageData.getInstance().JuzContentLengths)[juz - 1][position - 2];
            textString += " " + mDataset[position];
            textView.setText(textString);
        }


        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent));

        holder.rippleView.setOnClickListener(new RippleView.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);
                TextView textView = (TextView) rippleView.getChildAt(0);
                CharSequence text = textView.getText();
                int juzNumber;

                if (type == 0) {
                    String stringText = text.toString();

                    juzNumber = Integer.valueOf(stringText.substring(0, 2));

                    final Intent goToJuzContent = new Intent(view.getContext(), NavigationActivity.class);

                    goToJuzContent.putExtra("type", 1);

                    goToJuzContent.putExtra("juz number", juzNumber);

                    rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                        @Override
                        public void onComplete(RippleView rippleView) {
                            view.getContext().startActivity(goToJuzContent);
                        }
                        });

                } else if (type == 1) {



                    int quarter = chooseQuarter(text.toString(), view);

                    if (quarter < 5) {

                        final Intent goToJuz = new Intent(view.getContext(), MainActivity.class);

                        goToJuz.putExtra("type", 1);

                        goToJuz.putExtra("juz number", juz);

                        goToJuz.putExtra("new page number", QuranPageData.getInstance().JuzContentPageNumbers[juz-1][quarter]);

                        goToJuz.putExtra("from", "NavigationActivity");

                        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                            @Override
                            public void onComplete(RippleView rippleView) {
                                view.getContext().startActivity(goToJuz);
                                if (type == 1)
                                    ((Activity) view.getContext()).finishAffinity();
                            }
                        });
                    } else {
                        final Intent goToRuku = new Intent(view.getContext(), NavigationActivity.class);

                        goToRuku.putExtra("type", 2);

                        goToRuku.putExtra("juz number", juz);

                        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                            @Override
                            public void onComplete(RippleView rippleView) {
                                view.getContext().startActivity(goToRuku);
                                if (type == 2)
                                    ((Activity) view.getContext()).finishAffinity();
                            }
                        });
                    }
                } else if (type == 2) {
                    final Intent goToRukuAyah = new Intent(rippleView.getContext(), MainActivity.class);

                    goToRukuAyah.putExtra("from", "NavigationActivity");

                    int ruku = holder.getAdapterPosition();

                    int pageNumber = (QuranPageData.getInstance().RukuContentPageNumbers)[juz-1][ruku];

                    goToRukuAyah.putExtra("type", 2);
                    goToRukuAyah.putExtra("juz number", juz);
                    goToRukuAyah.putExtra("new page number", pageNumber);

                    rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                        @Override
                        public void onComplete(RippleView rippleView) {
                            view.getContext().startActivity(goToRukuAyah);
                            if (type == 2)
                                ((Activity) view.getContext()).finishAffinity();
                        }
                    });

                }

            }
        });

        holder.rippleView.setOnLongClickListener(new RippleView.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);

                TextView textView = (TextView) rippleView.getChildAt(0);
                CharSequence text = textView.getText();
                int juzNumber;

                if (type == 0) {
                    String stringText = text.toString();

                    juzNumber = Integer.valueOf(stringText.substring(0, 2));

                    final Intent goToJuz = new Intent(view.getContext(), MainActivity.class);

                    goToJuz.putExtra("from", "NavigationActivity");

                    goToJuz.putExtra("type", 0);

                    goToJuz.putExtra("new page number", QuranPageData.getInstance().JuzContentPageNumbers[juzNumber-1][0]);




                    rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                        @Override
                        public void onComplete(RippleView rippleView) {
                            view.getContext().startActivity(goToJuz);

                            ((Activity) view.getContext()).finish();
                        }
                    });
                    return true;
                }

                return false;
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public int chooseQuarter(String text, View view) {
        String[] arrays = (view.getContext().getResources().getStringArray(R.array.juz_content));

        if (text.equals(arrays[1]))
            return 0;

        text = text.substring(5);

        if (text.equals(arrays[2]))
            return 1;
        if (text.equals(arrays[3]))
            return 2;
        if (text.equals(arrays[4]))
            return 3;
        if (text.equals(arrays[5]))
            return 4;

        return 5;
    }
}

