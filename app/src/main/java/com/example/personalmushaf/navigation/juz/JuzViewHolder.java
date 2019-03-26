package com.example.personalmushaf.navigation.juz;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personalmushaf.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class JuzViewHolder extends GroupViewHolder {

    private TextView juzName;
    private ImageView arrow;

    public JuzViewHolder(View itemView) {
        super(itemView);
        juzName = (TextView) itemView.findViewById(R.id.list_item_juz_name);
        arrow = (ImageView) itemView.findViewById(R.id.list_item_juz_arrow);
    }

    public void setJuzTitle(ExpandableGroup juz) {
        if (juz instanceof Juz) {
            juzName.setText(juz.getTitle());
        }
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.setAnimation(rotate);
    }
}