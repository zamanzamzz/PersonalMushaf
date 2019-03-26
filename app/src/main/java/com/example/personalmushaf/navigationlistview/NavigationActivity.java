package com.example.personalmushaf.navigationlistview;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.os.Bundle;

import com.example.personalmushaf.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_navigation);
        // Init top level data
        List<String> listDataHeader = new ArrayList<>();
        String[] mItemHeaders = getResources().getStringArray(R.array.items_array_expandable_level_one);
        Collections.addAll(listDataHeader, mItemHeaders);
        final ExpandableListView mExpandableListView = (ExpandableListView) findViewById(R.id.expandableListView_Parent);
        if (mExpandableListView != null) {
            ParentLevelAdapter parentLevelAdapter = new ParentLevelAdapter(this, listDataHeader);
            mExpandableListView.setAdapter(parentLevelAdapter);

            mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                int previousGroup = -1;
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (groupPosition != previousGroup)
                        mExpandableListView.collapseGroup(previousGroup);
                    previousGroup = groupPosition;
                }
            });
        }
    }
}