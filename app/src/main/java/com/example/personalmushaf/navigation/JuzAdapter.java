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
    private String[] dataSet;
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

    // Provide a suitable constructor (depends on the kind of dataSet)
    public JuzAdapter(String[] myDataset, int type, int juz) {
        dataSet = myDataset;
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
        // - get element from your dataSet at this position
        // - replace the contents of the view with that element

        TextView textView = (TextView) holder.rippleView.getChildAt(0);
        textView.setText(dataSet[position]);
        textView.setTextDirection(View.TEXT_DIRECTION_LTR);

        prepJuzContentData(textView, position);

        alternateBackgroundColor(textView, position);

        holder.rippleView.setOnClickListener(new RippleView.OnClickListener() {
            @Override
            public void onClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);

                if (type == 0) {
                    juzToJuzContentProcedure(rippleView);
                } else if (type == 1) {
                    juzContentProcedure(rippleView, position);
                } else if (type == 2) {
                    rukuContentToPage(rippleView, holder.getAdapterPosition());

                }

            }
        });

        holder.rippleView.setOnLongClickListener(new RippleView.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                RippleView rippleView = (RippleView) view;
                rippleView.setRippleDuration(75);
                rippleView.setFrameRate(10);

                return juzContentToPage(rippleView);
            }
        });

    }


    // Return the size of your dataSet (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.length;
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


    private void alternateBackgroundColor(TextView textView, int position) {
        if (position % 2 == 0)
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorPrimary));
        else
            textView.setBackgroundColor(textView.getResources().getColor(R.color.colorAccent));
    }

    private void juzToJuzContentProcedure(RippleView rippleView) {
        TextView textView = (TextView) rippleView.getChildAt(0);
        CharSequence text = textView.getText();
        String stringText = text.toString();

        int selectedJuz = Integer.valueOf(stringText.substring(0, 2));

        final Intent goToJuzContent = new Intent(textView.getContext(), NavigationActivity.class);

        goToJuzContent.putExtra("type", 1);

        goToJuzContent.putExtra("juz number", selectedJuz);

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToJuzContent);
            }
        });
    }


    private void prepJuzContentData(TextView textView, int position) {
        if (type == 1 && position > 1) {
            String textString = (QuranPageData.getInstance().JuzContentLengths)[juz - 1][position - 2];
            textString += " " + dataSet[position];
            textView.setText(textString);
        }
    }

    private void juzContentProcedure(RippleView rippleView, int position) {
        TextView textView = (TextView) rippleView.getChildAt(0);
        CharSequence text = textView.getText();

        int quarter = chooseQuarter(text.toString(), textView);

        if (quarter < 5) {

            final Intent goToJuz = new Intent(textView.getContext(), MainActivity.class);

            goToJuz.putExtra("type", 1);

            goToJuz.putExtra("juz number", juz);

            goToJuz.putExtra("new page number", QuranPageData.getInstance().JuzContentPageNumbers[juz-1][quarter]);

            goToJuz.putExtra("from", "NavigationActivity");

            rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    rippleView.getContext().startActivity(goToJuz);
                    ((Activity) rippleView.getContext()).finishAffinity();
                }
            });
        } else {
            final Intent goToRuku = new Intent(textView.getContext(), NavigationActivity.class);

            goToRuku.putExtra("type", 2);

            goToRuku.putExtra("juz number", juz);

            rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    rippleView.getContext().startActivity(goToRuku);
                    if (type == 2)
                        ((Activity) rippleView.getContext()).finishAffinity();
                }
            });
        }
    }

    private void rukuContentToPage(RippleView rippleView, int selectedRuku) {
        final Intent goToRukuAyah = new Intent(rippleView.getContext(), MainActivity.class);

        goToRukuAyah.putExtra("from", "NavigationActivity");

        int pageNumber = (QuranPageData.getInstance().RukuContentPageNumbers)[juz-1][selectedRuku];

        goToRukuAyah.putExtra("type", 2);
        goToRukuAyah.putExtra("juz number", juz);
        goToRukuAyah.putExtra("new page number", pageNumber);

        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                rippleView.getContext().startActivity(goToRukuAyah);
                ((Activity) rippleView.getContext()).finishAffinity();
            }
        });
    }

    private boolean juzContentToPage(RippleView rippleView) {
        if (type == 0) {
            CharSequence text = ((TextView) rippleView.getChildAt(0)).getText();
            String stringText = text.toString();

            int juzNumber = Integer.valueOf(stringText.substring(0, 2));

            final Intent goToJuz = new Intent(rippleView.getContext(), MainActivity.class);

            goToJuz.putExtra("from", "NavigationActivity");

            goToJuz.putExtra("type", 0);

            goToJuz.putExtra("new page number", QuranPageData.getInstance().JuzContentPageNumbers[juzNumber-1][0]);

            rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {
                    rippleView.getContext().startActivity(goToJuz);

                    ((Activity) rippleView.getContext()).finish();
                }
            });
            return true;
        }

        return false;
    }

}

