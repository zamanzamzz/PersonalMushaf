package com.example.personalmushaf.navigation.ruku;

import android.view.View;
import android.widget.TextView;

import com.example.personalmushaf.R;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class RukuViewHolder extends ChildViewHolder {

    private TextView childTextView;

    public RukuViewHolder(View itemView) {
        super(itemView);
        childTextView = (TextView) itemView.findViewById(R.id.list_item_ruku_name);
    }

    public void setRukuName(String name) {
        childTextView.setText(name);
    }
}
