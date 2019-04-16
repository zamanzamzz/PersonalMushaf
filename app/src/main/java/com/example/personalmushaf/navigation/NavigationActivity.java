package com.example.personalmushaf.navigation;

import android.content.Intent;
import android.os.Bundle;

import com.example.personalmushaf.R;
import com.example.personalmushaf.navigation.adapters.JuzAdapter;
import com.example.personalmushaf.navigation.adapters.ViewPagerAdapter;
import com.example.personalmushaf.navigation.fragments.JuzFragment;
import com.example.personalmushaf.navigation.fragments.SurahFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


public class NavigationActivity extends AppCompatActivity {

    Toolbar navigationToolber;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    JuzAdapter juzAdapter;
    JuzFragment juzFragment;
    SurahFragment surahFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navigationToolber = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(navigationToolber);
        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();

        int receivingType = intent.getIntExtra("type", 0);
        int juzNumber = intent.getIntExtra("juz number", 0);

        juzFragment = new JuzFragment();
        surahFragment = new SurahFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("type", receivingType);
        arguments.putInt("juz number", juzNumber);
        juzFragment.setArguments(arguments);
        surahFragment.setArguments(arguments);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(juzFragment, "Juz");
        viewPagerAdapter.addFragment(surahFragment, "Surah");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
