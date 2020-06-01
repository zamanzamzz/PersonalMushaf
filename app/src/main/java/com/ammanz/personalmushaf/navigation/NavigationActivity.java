package com.ammanz.personalmushaf.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.ammanz.personalmushaf.QuranSettings;
import com.ammanz.personalmushaf.R;
import com.ammanz.personalmushaf.SettingsActivity;
import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterFragment;
import com.ammanz.personalmushaf.navigation.tabs.juztab.JuzFragment;
import com.ammanz.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;
import com.ammanz.personalmushaf.navigation.tabs.surahtab.SurahFragment;
import com.google.android.material.tabs.TabLayout;

public class NavigationActivity extends AppCompatActivity {
    MushafMetadata mushafMetadata;
    int juzNumber;
    int currentPagerPosition;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar navigationToolbar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(navigationToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mushafMetadata = QuranSettings.getInstance().getMushafMetadata(this);

        Intent intent = getIntent();

        juzNumber = intent.getIntExtra("juz number", -1);
        currentPagerPosition = 0;

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        // viewPager.setSwipeEnabled(false);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        if (juzNumber < 0) {
            TextView title = findViewById(R.id.juz_title_toolbar);
            title.setText("Qur'an Contents");

            JuzFragment juzFragment = new JuzFragment();
            SurahFragment surahFragment = new SurahFragment();

            Bundle arguments = new Bundle();
            arguments.putInt("juz number", juzNumber);

            juzFragment.setArguments(arguments);
            surahFragment.setArguments(arguments);

            viewPagerAdapter.addFragment(juzFragment, "Juz");
            viewPagerAdapter.addFragment(surahFragment, "Surah");
        } else {

            TextView juzTitle = findViewById(R.id.juz_title_toolbar);
            setJuzTitle(juzTitle, juzNumber);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);

            TextView juzStart = findViewById(R.id.juz_start_toolbar);

            juzStart.setText(juzStart.getResources().getStringArray(R.array.juz_names)[juzNumber - 1]);

            JuzQuarterFragment juzQuarterFragment = new JuzQuarterFragment();
            RukuContentFragment rukuContentFragment = new RukuContentFragment();
            SurahFragment surahFragment = new SurahFragment();

            Bundle arguments = new Bundle();
            arguments.putInt("juz number", juzNumber);

            juzQuarterFragment.setArguments(arguments);
            rukuContentFragment.setArguments(arguments);
            surahFragment.setArguments(arguments);

            viewPagerAdapter.addFragment(juzQuarterFragment, "Quarter");
            setViewPagerTabs(viewPagerAdapter, rukuContentFragment);
            viewPagerAdapter.addFragment(surahFragment, "Surah");
        }

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (juzNumber < 0) {
            MenuInflater inflater = getMenuInflater();

            inflater.inflate(R.menu.settings, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

            return true;
        } else if (id == R.id.go_to_settings_navigation) {
            Intent goToSettings = new Intent(this, SettingsActivity.class);

            this.startActivity(goToSettings);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setJuzTitle(TextView juzTitle, int juzNumber) {
        String length = String.format("%.2f", getJuzLength(juzNumber));
        String title = getResources().getStringArray(R.array.arabic_numerals)[juzNumber - 1] + "  | " + length + " pages";

        juzTitle.setText(title);
    }

    private double getJuzLength(int juzNumber) {
        return mushafMetadata.getNavigationData().getJuzLengths()[juzNumber - 1];
    }

    private void setViewPagerTabs(ViewPagerAdapter viewPagerAdapter, RukuContentFragment rukuContentFragment) {
        int landmarkSystem = QuranSettings.getInstance().getLandMarkSystem(this);
        if (mushafMetadata.getShouldDoRuku(landmarkSystem))
            viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
    }
}
