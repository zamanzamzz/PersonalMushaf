package com.example.personalmushaf.navigation;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.personalmushaf.R;
import com.example.personalmushaf.SettingsActivity;
import com.example.personalmushaf.navigation.tabs.juztab.JuzFragment;
import com.example.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterFragment;
import com.example.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;
import com.example.personalmushaf.navigation.tabs.surahtab.SurahFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


public class NavigationActivity extends AppCompatActivity {

    private static final int  REQUEST_READ_EXTERNAL_STORAGE = 111;
    Toolbar navigationToolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    JuzFragment juzFragment;
    JuzQuarterFragment juzQuarterFragment;
    RukuContentFragment rukuContentFragment;
    SurahFragment surahFragment;
    SharedPreferences preferences;
    String mushafVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        navigationToolbar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(navigationToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        mushafVersion = preferences.getString("mushaf", "madani_15_line");

        Intent intent = getIntent();

        int juzNumber = intent.getIntExtra("juz number", -1);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE);
        }

        if (juzNumber < 0) {
            TextView title = findViewById(R.id.juz_title_toolbar);
            title.setText("Qur'an Contents");

            juzFragment = new JuzFragment();
            surahFragment = new SurahFragment();

            Bundle arguments = new Bundle();
            arguments.putInt("juz number", juzNumber);

            juzFragment.setArguments(arguments);
            surahFragment.setArguments(arguments);

            viewPagerAdapter.addFragment(juzFragment, "Juz");
            viewPagerAdapter.addFragment(surahFragment, "Surah");
        } else {
            String title;

            if (mushafVersion.equals("naskh_13_line"))
                title = NaskhThirteenLinePageData.juzInfo[juzNumber-1][0] + "  | " +
                        NaskhThirteenLinePageData.juzInfo[juzNumber-1][1] + " pages";
            else
                title = MadaniFifteenLinePageData.juzInfo[juzNumber-1][0] + "  | " +
                        MadaniFifteenLinePageData.juzInfo[juzNumber-1][1] + " pages";

            TextView juzTitle = findViewById(R.id.juz_title_toolbar);
            juzTitle.setText(title);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);

            TextView juzStart = findViewById(R.id.juz_start_toolbar);

            juzStart.setText(NaskhThirteenLinePageData.juzInfo[juzNumber-1][2]);

            juzQuarterFragment = new JuzQuarterFragment();
            rukuContentFragment = new RukuContentFragment();
            surahFragment = new SurahFragment();

            Bundle arguments = new Bundle();
            arguments.putInt("juz number", juzNumber);

            juzQuarterFragment.setArguments(arguments);
            rukuContentFragment.setArguments(arguments);
            surahFragment.setArguments(arguments);

            viewPagerAdapter.addFragment(juzQuarterFragment, "Quarter");
            if (mushafVersion.equals("naskh_13_line"))
                viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
            viewPagerAdapter.addFragment(surahFragment, "Surah");
        }

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.settings, menu);

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
        }

        return super.onOptionsItemSelected(item);
    }


}
