package com.ammanz.personalmushaf;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.ammanz.personalmushaf.navigation.NavigationActivity;

public class SettingsActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private static final String TITLE_TAG = "settingsActivityTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new HeaderFragment())
                    .commit();
        } else {
            setTitle(savedInstanceState.getCharSequence(TITLE_TAG));
        }
        getSupportFragmentManager().addOnBackStackChangedListener(
                () -> {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        setTitle(R.string.title_activity_settings);
                    }
                });
        Toolbar navigationToolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(navigationToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        TextView toolbarTitle = findViewById(R.id.settings_title_toolbar);
        toolbarTitle.setText("Settings");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save current activity title so we can set it again after a configuration change
        outState.putCharSequence(TITLE_TAG, getTitle());
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getSupportFragmentManager().popBackStackImmediate()) {
            return true;
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        // Instantiate the new Fragment
        final Bundle args = pref.getExtras();
        final Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(),
                pref.getFragment());
        fragment.setArguments(args);
        fragment.setTargetFragment(caller, 0);
        // Replace the existing Fragment with the new Fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings, fragment)
                .addToBackStack(null)
                .commit();
        setTitle(pref.getTitle());
        return true;
    }

    public static class HeaderFragment extends PreferenceFragmentCompat {
        private Preference mushaf;
        private Integer currentLandMarkSystem;
        private String currentMushaf;
        private Boolean isInterfaceSimplified;
        private QuranSettings quranSettings;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.header_preferences, rootKey);
            quranSettings = QuranSettings.getInstance();
            mushaf = findPreference("mushaf");
            currentLandMarkSystem = quranSettings.getLandMarkSystem(getContext());
            currentMushaf = quranSettings.getMushafMetadata(getContext()).getId();
            isInterfaceSimplified = quranSettings.getSimplifyInterface(getContext());
            SwitchPreferenceCompat isNightMode = findPreference("night_mode");
            SwitchPreferenceCompat simplifyInterface = findPreference("simplify_interface");
            ListPreference landmarkSystem = findPreference("landmark");
            SwitchPreferenceCompat isSmoothKeyNavigation = findPreference("smoothpageturn");
            SwitchPreferenceCompat isDebugMode = findPreference("debugmodeswitch");

            mushaf.setSummary(quranSettings.getMushafMetadata(getContext()).getName());

            isNightMode.setOnPreferenceChangeListener((preference, newValue) -> {
                quranSettings.setNightMode((Boolean) newValue);
                return true;
            });

            simplifyInterface.setOnPreferenceChangeListener((preference, newValue) -> {
                quranSettings.setSimplifyInterface((Boolean) newValue);
                if (isInterfaceSimplified != (Boolean) newValue)
                    quranSettings.setShouldRestartNavigationActivity(true);
                return true;
            });

            landmarkSystem.setOnPreferenceChangeListener((preference, newValue) -> {
                int landmarkSystem1 = Integer.parseInt((String) newValue);
                quranSettings.setLandmarkSystem(landmarkSystem1);
                if (landmarkSystem1 != currentLandMarkSystem) {
                    quranSettings.setShouldRestartNavigationActivity(true);
                } else
                    quranSettings.setShouldRestartNavigationActivity(false);
                return true;
            });

            isSmoothKeyNavigation.setOnPreferenceChangeListener((preference, newValue) -> {
                quranSettings.setSmoothKeyNavigation((Boolean) newValue);
                return true;
            });

            isDebugMode.setOnPreferenceChangeListener((preference, newValue) -> {
                quranSettings.setDebugMode((Boolean) newValue);
                return true;
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            if (!quranSettings.getMushafMetadata(getContext()).getId().equals(currentMushaf)) {
                mushaf.setSummary(quranSettings.getMushafMetadata(getContext()).getName());
                quranSettings.setShouldRestartNavigationActivity(true);
            } else {
                quranSettings.setShouldRestartNavigationActivity(false);
            }

        }
    }
}
