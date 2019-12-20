package com.android.personalmushaf.startup;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.personalmushaf.R;
import com.android.personalmushaf.navigation.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StartupMushafStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        final ViewPager2 viewPager = findViewById(R.id.startup_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        int mushafType = getIntent().getIntExtra("mushaf_type", 15);

        populateViewPager(getMushafDirectory(mushafType), getPreviewDrawableID(mushafType),
                getMushafTitle(mushafType), getMushafDescriptions(mushafType), adapter);

        viewPager.setAdapter(adapter);
    }

    private List<String> getMushafDirectory(int mushafType)  {
        List<String> titles = new ArrayList<>();
        switch (mushafType) {
            case 13:
                titles.add("naskh_13_line");
                break;
            case 15:
                titles.add("madani_15_line");
                break;
        }
        return titles;
    }

    private List<Integer> getPreviewDrawableID(int mushafType)  {
        List<Integer> ids = new ArrayList<>();
        switch (mushafType) {
            case 13:
                ids.add(R.drawable.naskh_13_line_preview);
                break;
            case 15:
                ids.add(R.drawable.madani_15_line_preview);
                break;
        }
        return ids;
    }

    private List<String> getMushafTitle(int mushafType)  {
        List<String> titles = new ArrayList<>();
        switch (mushafType) {
            case 13:
                titles.add("Modern 13 Line Naskh Mushaf");
                break;
            case 15:
                titles.add("Classic 15 Line Madani Mushaf");
                break;
        }
        return titles;
    }

    private List<String> getMushafDescriptions(int mushafType)  {
        List<String> descriptions = new ArrayList<>();
        switch (mushafType) {
            case 13:
                descriptions.add("Popular with huffadh in the Indian Subcontinent and South Africa.");
                break;
            case 15:
                descriptions.add("The classic standard mushaf from Saudi Arabia.");
                break;
        }
        return descriptions;
    }

    private void populateViewPager(List<String> mushafDirectories, List<Integer> previewIDs, List<String> previewTitles,
                                   List<String> previewDescriptions, ViewPagerAdapter adapter) {
        Bundle arguments;
        Fragment fragment;
        for (int i = 0; i < previewIDs.size(); i++) {
            arguments = new Bundle();
            arguments.putString("mushaf_directory", mushafDirectories.get(i));
            arguments.putInt("drawable_id", previewIDs.get(i));
            arguments.putString("mushaf_style_title", previewTitles.get(i));
            arguments.putString("mushaf_style_description", previewDescriptions.get(i));
            fragment = new StartupFragment();
            fragment.setArguments(arguments);
            adapter.addFragment(fragment, "");
        }
    }

}
