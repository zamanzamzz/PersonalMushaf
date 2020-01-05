package com.android.personalmushaf.startup;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.mushafinterfaces.mushafmetadata.MushafMetadataFactory;
import com.android.personalmushaf.navigation.ViewPagerAdapter;

public class StartupMushafStyleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        final ViewPager viewPager = findViewById(R.id.startup_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        int mushafType = getIntent().getIntExtra("mushaf_type", QuranSettings.CLASSIC_MADANI_15_LINE);

        populateViewPager(adapter, mushafType);

        viewPager.setAdapter(adapter);
    }

    private void populateViewPager(ViewPagerAdapter adapter, int mushafType) {
        Bundle arguments;
        Fragment fragment;
        for (int i = 0; i < MushafMetadataFactory.mushafStructure[mushafType].length; i++) {
            arguments = new Bundle();
            arguments.putInt("mushaf", MushafMetadataFactory.mushafStructure[mushafType][i]);
            fragment = new StartupFragment();
            fragment.setArguments(arguments);
            adapter.addFragment(fragment, "");
        }
    }

}
