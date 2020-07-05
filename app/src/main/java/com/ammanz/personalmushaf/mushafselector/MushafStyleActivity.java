package com.ammanz.personalmushaf.mushafselector;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadataFactory;
import com.ammanz.personalmushaf.navigation.ViewPagerAdapter;

public class MushafStyleActivity extends AppCompatActivity {
    private boolean fromSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mushafstyle);
        final ViewPager viewPager = findViewById(R.id.mushafstyle_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        int mushafType = getIntent().getIntExtra("mushaf_type", QuranSettings.CLASSICMADANI15);

        fromSettings = getIntent().getBooleanExtra("from_settings", false);

        populateViewPager(adapter, mushafType);

        viewPager.setClipToPadding(false);

        viewPager.setPadding(70,0,70,0);

        viewPager.setPageMargin(50);

        viewPager.setAdapter(adapter);
    }

    private void populateViewPager(ViewPagerAdapter adapter, int mushafType) {
        Bundle arguments;
        Fragment fragment;
        for (int i = 0; i < MushafMetadataFactory.mushafStructure[mushafType].length; i++) {
            arguments = new Bundle();
            arguments.putInt("mushaf", MushafMetadataFactory.mushafStructure[mushafType][i]);
            arguments.putBoolean("from_settings", fromSettings);
            fragment = new MushafStyleFragment();
            fragment.setArguments(arguments);
            adapter.addFragment(fragment, "");
        }
    }

}
