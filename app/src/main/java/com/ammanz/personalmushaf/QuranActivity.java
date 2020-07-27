package com.ammanz.personalmushaf;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.ammanz.personalmushaf.mushafmetadata.MushafMetadata;
import com.ammanz.personalmushaf.navigation.ViewPagerAdapter;
import com.ammanz.personalmushaf.navigation.tabs.juzquartertab.JuzQuarterFragment;
import com.ammanz.personalmushaf.navigation.tabs.juztab.JuzFragment;
import com.ammanz.personalmushaf.navigation.tabs.rukucontenttab.RukuContentFragment;
import com.ammanz.personalmushaf.navigation.tabs.surahtab.SurahFragment;
import com.ammanz.personalmushaf.quranpage.QuranPageAdapter;
import com.ammanz.personalmushaf.util.GlyphsHighlighter;
import com.google.android.material.tabs.TabLayout;

import java.util.Observable;
import java.util.Observer;


public class QuranActivity extends AppCompatActivity implements Observer {

    private Toolbar toolbar;
    private View toolbarArea;

    public Integer highlightedSurah = null;
    public Integer highlightedAyah = null;
    public boolean isHighlighted = false;
    public boolean currentShouldUseDualPages;

    private int pageNumber;
    private int receivedPageNumber;
    private int pagesTurned;

    private ViewPager2 pager;

    private ViewPager2.OnPageChangeCallback singlePageChangeCallback;
    private ViewPager2.OnPageChangeCallback dualPageChangeCallback;


    private QuranPageAdapter pagerAdapter;

    boolean isSmoothVolumeKeyNavigation;
    private MushafMetadata mushafMetadata;

    private GlyphsHighlighter glyphsHighlighter;
    private QuranSettings quranSettings;
    private static final String ARC_DEVICE_PATTERN = ".+_cheets|cheets_.+";
    private boolean isChromebook;
    private boolean isToolbarVisible = true;

    private int juzNumber;
    private int currentPagerPosition;
    private ViewPagerAdapter viewPagerAdapter;
    private View navigationDrawer;
    private boolean isDrawerVisible = false;

    private int navigationDrawerPadding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);
        if (Build.VERSION.SDK_INT >= 27)
            setShowWhenLocked(true);
        else
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        quranSettings = QuranSettings.getInstance();

        loadNavigationDrawer();
        
        isSmoothVolumeKeyNavigation = quranSettings.getIsSmoothKeyNavigation(this);

        setupActionbar();

        Intent activityThatCalled = getIntent();

        receivedPageNumber = activityThatCalled.getIntExtra("new page number", 2);

        setPageNumberAndPagesTurned(savedInstanceState);

        currentShouldUseDualPages = shouldUseDualPage();

        mushafMetadata = quranSettings.getMushafMetadata(this);

        setupInitialPager();

        isChromebook = Build.DEVICE != null && Build.DEVICE.matches(ARC_DEVICE_PATTERN);

        hideSystemUI();
        toolbarArea.post(() -> {
            isToolbarVisible = false;
            animateToolbar(false);
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentPage", pageNumber);
        outState.putInt("pagesTurned", pagesTurned);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
        isToolbarVisible = false;
        animateToolbar(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        glyphsHighlighter.stopHighlight();

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            destroyPager();
            currentShouldUseDualPages = shouldUseDualPage();
            setupSinglePager();
            glyphsHighlighter.setPager(pager);
            glyphsHighlighter.setPagerAdapter(pagerAdapter);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            destroyPager();
            currentShouldUseDualPages = shouldUseDualPage();
            setupDualPager();
            glyphsHighlighter.setPager(pager);
            glyphsHighlighter.setPagerAdapter(pagerAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (quranSettings.getIsDebugMode(this))
            getMenuInflater().inflate(R.menu.glyphplayback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            animateDrawer(!isDrawerVisible);
            return true;
        } else if (id == R.id.glyph_play_pause) {
            glyphsHighlighter.pausePlayHighlight();
            return true;
        } else if (id == R.id.glyph_forward) {
            glyphsHighlighter.forwardHighlight();
            return true;
        } else if (id == R.id.glyph_backward) {
            glyphsHighlighter.reverseHighlight();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        destroyPager();
        glyphsHighlighter.stopHighlight();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                flipPageBackward(currentShouldUseDualPages ? 2 : 1);
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                flipPageForward(currentShouldUseDualPages ? 2 : 1);
                break;

            case KeyEvent.KEYCODE_BACK:
                destroyPager();
                glyphsHighlighter.stopHighlight();
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (currentShouldUseDualPages && action == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    if (getScreenRotation() == Surface.ROTATION_90)
                        flipPageBackward(2);
                    else
                        flipPageForward(2);

                    vibrate(v, 100);
                    return true;

                case KeyEvent.KEYCODE_VOLUME_UP:
                    if (getScreenRotation() == Surface.ROTATION_90)
                        flipPageForward(2);
                    else
                        flipPageBackward(2);

                    vibrate(v, 100);
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    private void setupActionbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbarArea = findViewById(R.id.toolbar_parent);
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        int statusBarHeight = getStatusBarHeight();
        if (statusBarHeight <= convertDpToPixel(24)) {
            params.height = params.height + getStatusBarHeight();
            toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
            navigationDrawerPadding = getStatusBarHeight();
        } else {
            navigationDrawerPadding = 0;
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);
    }




    private void setPageNumberAndPagesTurned(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            pageNumber = receivedPageNumber;
            pagesTurned = 0;
        } else {
            pagesTurned = savedInstanceState.getInt("pagesTurned");
            pageNumber = savedInstanceState.getInt("currentPage");
        }
    }


    private void setupInitialPager() {

        singlePageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageNumber = singlePagerPositionToPageNumber(position);
                pagesTurned++;
                if (pagesTurned >= 8){
                    System.gc();
                    pagesTurned = 0;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        dualPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageNumber = dualPagerPositionToPageNumber(position);
                pagesTurned += 2;
                if (pagesTurned >= 8){
                    System.gc();
                    pagesTurned = 0;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        highlightAyahFromNavigation();
        if (!currentShouldUseDualPages) {
            setupSinglePager();
        } else {
            setupDualPager();
        }

        glyphsHighlighter = new GlyphsHighlighter(pager, pagerAdapter);
    }

    private void setupSinglePager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(),this, getLifecycle());
        if (quranSettings.getNightMode(this))
            pager.setBackgroundColor(Color.BLACK);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.post(() -> {
            pager.setCurrentItem(pageNumberToSinglePagerPosition(pageNumber), false);
            pager.registerOnPageChangeCallback(singlePageChangeCallback);
        });
    }

    private void setupDualPager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(),this, getLifecycle());
        if (quranSettings.getNightMode(this))
            pager.setBackgroundColor(Color.BLACK);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.post(() -> {
            pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber), false);
            pager.registerOnPageChangeCallback(dualPageChangeCallback);
        });
    }

    private void destroyPager() {
        if (currentShouldUseDualPages)
            pager.unregisterOnPageChangeCallback(dualPageChangeCallback);
        else
            pager.unregisterOnPageChangeCallback(singlePageChangeCallback);
        pagerAdapter = null;
        pager = null;
        System.gc();
    }


    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int convertDpToPixel (float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public void SystemUIListener(View view) {
        if (isDrawerVisible) {
            isDrawerVisible = false;
            animateDrawer(false);

        }
        isToolbarVisible = !isToolbarVisible;
        animateToolbar(isToolbarVisible);
        if (!isChromebook) {
            if (getWindow().getDecorView().getSystemUiVisibility() == (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN))
                hideSystemUI();
            else
                showSystemUI();
        }
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    private void animateToolbar(boolean visible) {
        toolbarArea.animate()
                .translationY(visible ? 0 : -toolbarArea.getHeight())
                .setDuration(250)
                .start();
    }

    public boolean shouldUseDualPage() {
        Display screensize = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        screensize.getSize(size);
        int width = size.x;
        int height = size.y;
        return width >= height;
    }

    private int getScreenRotation() {
        return ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }

    private void vibrate(Vibrator v, int time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(time);
        }
    }

    private int pageNumberToDualPagerPosition(int pageNumber) {
        if (pageNumber % 2 == 0)
            return pageNumber / 2 - 1;
        else
            return (pageNumber - 1) / 2 - (mushafMetadata.getMinPage() - 1);
    }

    private int dualPagerPositionToPageNumber(int dualPagerPosition) {
        return dualPagerPosition * 2 + mushafMetadata.getMinPage();
    }

    private int pageNumberToSinglePagerPosition(int pageNumber) {
        return pageNumber - mushafMetadata.getMinPage();
    }

    private int singlePagerPositionToPageNumber(int position) {
        return position + mushafMetadata.getMinPage();
    }

    private void flipPageBackward(int pagesToFlip) {
        if (pageNumber - pagesToFlip >= mushafMetadata.getMinPage()) {
            pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber - pagesToFlip), isSmoothVolumeKeyNavigation);
        }
    }

    private void flipPageForward(int pagesToFlip) {
        if (pageNumber + pagesToFlip <= mushafMetadata.getMaxPage()) {
            pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber + pagesToFlip), isSmoothVolumeKeyNavigation);
        }
    }

    public void update(Observable o, Object arg) {
        Bundle arguments = (Bundle) arg;
        String[] ayahKey = arguments.getString("ayah_key").split(":");
        int position = arguments.getInt("position");
        Integer selectedSurah = Integer.parseInt(ayahKey[0]);
        Integer selectedAyah = Integer.parseInt(ayahKey[1]);
        if (!isHighlighted) {
            toolbar.setTitle(selectedSurah + ":" + selectedAyah);
            pagerAdapter.highlightVisiblePages(position, selectedSurah, selectedAyah);
            highlightedSurah = selectedSurah;
            highlightedAyah = selectedAyah;
            isHighlighted = true;
        } else if (selectedSurah.equals(highlightedSurah) && selectedAyah.equals(highlightedAyah)) {
            toolbar.setTitle("");
            pagerAdapter.unhighlightVisiblePages(position, selectedSurah, selectedAyah);
            highlightedSurah = null;
            highlightedAyah = null;
            isHighlighted = false;
        } else {
            toolbar.setTitle(selectedSurah + ":" + selectedAyah);
            pagerAdapter.highlightVisiblePages(position, selectedSurah, selectedAyah);
            highlightedSurah = selectedSurah;
            highlightedAyah = selectedAyah;
            isHighlighted = true;
        }
    }

    private void highlightAyahFromNavigation() {
        highlightedSurah = getIntent().getIntExtra("surah", 0);
        highlightedAyah = getIntent().getIntExtra("ayah", 0);

        if (highlightedSurah != 0) {
            isHighlighted = true;
            toolbar.setTitle(highlightedSurah + ":" + highlightedAyah);
        }
    }

    private void loadNavigationDrawer() {
        Toolbar navigationToolbar = findViewById(R.id.navigation_toolbar);
        setSupportActionBar(navigationToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mushafMetadata = quranSettings.getMushafMetadata(this);

        Intent intent = getIntent();

        juzNumber = intent.getIntExtra("juz number", -1);
        currentPagerPosition = 0;

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.viewpager);
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


        TextView title = findViewById(R.id.juz_title_toolbar);
        if (juzNumber < 0) {
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

            setJuzTitle(title, juzNumber);

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

        navigationDrawer = findViewById(R.id.side_panel);
        navigationDrawer.post(() -> {
            navigationDrawer.setTranslationX(-navigationDrawer.getWidth());
            isDrawerVisible = false;
        });
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        navigationToolbar.post(() -> {
            navigationDrawer.setPadding(0, navigationDrawerPadding + navigationToolbar.getHeight(), 0, 0);
        });
    }

    private void animateDrawer(boolean visible) {
        isDrawerVisible = visible;
        navigationDrawer.animate()
                .translationX(visible ? 0 : -navigationDrawer.getWidth())
                .setDuration(250)
                .start();
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
        int landmarkSystem = quranSettings.getLandMarkSystem(this);
        if (mushafMetadata.getShouldDoRuku(landmarkSystem))
            viewPagerAdapter.addFragment(rukuContentFragment, "Ruku");
    }
}
