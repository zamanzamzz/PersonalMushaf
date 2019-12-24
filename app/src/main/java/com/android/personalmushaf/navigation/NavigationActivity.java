package com.android.personalmushaf.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.android.personalmushaf.QuranSettings;
import com.android.personalmushaf.R;
import com.android.personalmushaf.SettingsActivity;
import com.android.personalmushaf.mushafinterfaces.strategies.navigationstrategies.NavigationActivityStrategy;
import com.android.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterFragment;
import com.android.personalmushaf.navigation.tabs.juztab.JuzFragment;
import com.android.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;
import com.android.personalmushaf.navigation.tabs.surahtab.SurahFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NavigationActivity extends AppCompatActivity {
    NavigationActivityStrategy navigationActivityStrategy;
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

        navigationActivityStrategy = QuranSettings.getInstance().getMushafStrategy(this).getNavigationActivityStrategy();

        Intent intent = getIntent();

        juzNumber = intent.getIntExtra("juz number", -1);
        currentPagerPosition = 0;

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPagerPosition = position;
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
            navigationActivityStrategy.setViewPagerTabs(viewPagerAdapter, rukuContentFragment);
            viewPagerAdapter.addFragment(surahFragment, "Surah");
        }

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(viewPagerAdapter.getPageTitle(position));
            }
        }).attach();
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
        String title = getResources().getStringArray(R.array.arabic_numerals)[juzNumber - 1] + "  | " + navigationActivityStrategy.getJuzLength(juzNumber) + " pages";

        juzTitle.setText(title);
    }
}
