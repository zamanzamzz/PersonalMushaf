package com.android.personalmushaf;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.android.personalmushaf.mushafmetadata.MushafMetadata;
import com.android.personalmushaf.quranpage.QuranPageAdapter;
import com.duolingo.open.rtlviewpager.RtlViewPager;

import java.util.Observable;
import java.util.Observer;


public class QuranActivity extends AppCompatActivity implements Observer {

    private Toolbar toolbar;

    public Integer highlightedSurah = null;
    public Integer highlightedAyah = null;
    public boolean isHighlighted = false;

    private int pageNumber;
    private int receivedPageNumber;
    private int pagesTurned;

    private int currentOrientation;

    private RtlViewPager pager;

    private ViewPager.OnPageChangeListener singlePageChangeListener;
    private ViewPager.OnPageChangeListener dualPageChangeListener;


    private QuranPageAdapter pagerAdapter;

    boolean isSmoothVolumeKeyNavigation;
    boolean isForceDualPage;
    private MushafMetadata mushafMetadata;

    private GlyphsHighlighter glyphsHighlighter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);
        
        isSmoothVolumeKeyNavigation = QuranSettings.getInstance().getIsSmoothKeyNavigation(this);
        isForceDualPage = QuranSettings.getInstance().getIsForceDualPage(this);

        setupActionbar();

        Intent activityThatCalled = getIntent();

        receivedPageNumber = activityThatCalled.getIntExtra("new page number", 2);

        setPageNumberAndPagesTurned(savedInstanceState);

        currentOrientation = getScreenRotation();

        mushafMetadata = QuranSettings.getInstance().getMushafMetadata(this);

        setupInitialPager();

        setOnSystemUiVisibilityChangeListener();

        glyphsHighlighter = new GlyphsHighlighter(pager, pagerAdapter);

        hideSystemUI();
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
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            destroyPager();
            currentOrientation = getScreenRotation();
            setupSinglePager();

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            destroyPager();
            currentOrientation = getScreenRotation();
            setupDualPager();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (QuranSettings.getInstance().getIsDebugMode(this))
            getMenuInflater().inflate(R.menu.glyphplayback, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            destroyPager();
            glyphsHighlighter.stopHighlight();
            finish();
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
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (isLandscape(currentOrientation) && action == KeyEvent.ACTION_DOWN) {
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
        ViewGroup.LayoutParams params = toolbar.getLayoutParams();
        params.height = params.height + getStatusBarHeight();
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        actionBar.hide();
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

        singlePageChangeListener = new ViewPager.OnPageChangeListener() {
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

        dualPageChangeListener = new ViewPager.OnPageChangeListener() {
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

        if (!isLandscape(currentOrientation) && !isForceDualPage) {
            setupSinglePager();
        } else {
            setupDualPager();
        }

        highlightAyahFromNavigation();
    }

    private void setupSinglePager() {
        pager = findViewById(R.id.pager);

        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(),this, currentOrientation);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.setCurrentItem(pageNumberToSinglePagerPosition(pageNumber), false);
        pager.addOnPageChangeListener(singlePageChangeListener);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void setupDualPager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(),this, currentOrientation);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber), false);
        pager.addOnPageChangeListener(dualPageChangeListener);
        if (isForceDualPage)
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    private void destroyPager() {
        if (isLandscape(currentOrientation))
            pager.removeOnPageChangeListener(dualPageChangeListener);
        else
            pager.removeOnPageChangeListener(singlePageChangeListener);
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

    private int getScreenRotation() {
        return ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }

    public void SystemUIListener(View view) {
        if (getWindow().getDecorView().getSystemUiVisibility() == (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN))
            hideSystemUI();
        else
            showSystemUI();
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    private void actionOnSystemUIChange(int visibility) {
        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
            getSupportActionBar().show();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            getSupportActionBar().hide();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    public static boolean isLandscape(int currentOrientation) {
        return currentOrientation == Surface.ROTATION_90 || currentOrientation == Surface.ROTATION_270;
    }

    private void vibrate(Vibrator v, int time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(time);
        }
    }

    private void setOnSystemUiVisibilityChangeListener() {
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener
                (visibility -> actionOnSystemUIChange(visibility));
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
        final int surahFromNavigation = getIntent().getIntExtra("surah", 0);
        final int ayahFromNavigation = getIntent().getIntExtra("ayah", 0);

        pager.post(() -> {
            if (surahFromNavigation != 0) {
                highlightedSurah = surahFromNavigation;
                highlightedAyah = ayahFromNavigation;
                isHighlighted = true;
                pagerAdapter.highlightVisiblePages(pager.getCurrentItem(), highlightedSurah, highlightedAyah);
                toolbar.setTitle(highlightedSurah + ":" + highlightedAyah);
            }
        });
    }
}
