package com.example.personalmushaf.navigationlistview;

import android.content.Context;
import android.widget.ExpandableListView;

public class NavigationListView extends ExpandableListView {

    public NavigationListView(Context context)
    {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(960, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(20000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
