package com.android.personalmushaf;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.android.personalmushaf.navigation.NavigationActivity;

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
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.header_preferences, rootKey);
            mushaf = findPreference("mushaf");
            ListPreference landmarkSystem = findPreference("landmark");
            SwitchPreferenceCompat isForceDualPages = findPreference("force_dual_page");
            SwitchPreferenceCompat isSmoothKeyNavigation = findPreference("smoothpageturn");
            SwitchPreferenceCompat isDebugMode = findPreference("debugmodeswitch");

            mushaf.setSummary(QuranSettings.getInstance().getMushafMetadata(getContext()).getName());


            landmarkSystem.setOnPreferenceChangeListener((preference, newValue) -> {
                int landmarkSystem1 = Integer.parseInt((String) newValue);
                QuranSettings.getInstance().setLandmarkSystem(landmarkSystem1);
                return true;
            });

            isForceDualPages.setOnPreferenceChangeListener((preference, newValue) -> {
                QuranSettings.getInstance().setForceDualPage((Boolean) newValue);
                return true;
            });

            isSmoothKeyNavigation.setOnPreferenceChangeListener((preference, newValue) -> {
                QuranSettings.getInstance().setSmoothKeyNavigation((Boolean) newValue);
                return true;
            });

            isDebugMode.setOnPreferenceChangeListener((preference, newValue) -> {
                QuranSettings.getInstance().setDebugMode((Boolean) newValue);
                return true;
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            mushaf.setSummary(QuranSettings.getInstance().getMushafMetadata(getContext()).getName());
        }
    }
}
