package com.example.personalmushaf.navigation;

import android.content.Intent;
import android.os.Bundle;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.tabs.juztab.JuzFragment;
import com.example.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterFragment;
import com.example.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;
import com.example.personalmushaf.navigation.tabs.surahtab.SurahFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


public class NavigationActivity extends AppCompatActivity {

    Toolbar navigationToolber;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    JuzFragment juzFragment;
    JuzQuarterFragment juzQuarterFragment;
    RukuContentFragment rukuContentFragment;
    SurahFragment surahFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navigationToolber = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(navigationToolber);

        Intent intent = getIntent();

        int juzNumber = intent.getIntExtra("juz number", -1);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        if (juzNumber < 0) {
            getSupportActionBar().setTitle("Qur'an Contents");

            juzFragment = new JuzFragment();
            surahFragment = new SurahFragment();

            Bundle arguments = new Bundle();
            arguments.putInt("juz number", juzNumber);

            juzFragment.setArguments(arguments);
            surahFragment.setArguments(arguments);

            viewPagerAdapter.addFragment(juzFragment, "Juz");
            viewPagerAdapter.addFragment(surahFragment, "Surah");
        } else {
            getSupportActionBar().setTitle("Chapter " + QuranPageData.getInstance().juzTitles[juzNumber-1]);

            juzQuarterFragment = new JuzQuarterFragment();
            rukuContentFragment = new RukuContentFragment();
            surahFragment = new SurahFragment();

            Bundle arguments = new Bundle();
            arguments.putInt("juz number", juzNumber);

            juzQuarterFragment.setArguments(arguments);
            rukuContentFragment.setArguments(arguments);
            surahFragment.setArguments(arguments);

            viewPagerAdapter.addFragment(juzQuarterFragment, "Quarter");
            viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
            viewPagerAdapter.addFragment(surahFragment, "Surah");
        }

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
