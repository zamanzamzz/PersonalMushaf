package com.example.personalmushaf;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.personalmushaf.quranpage.QuranPageAdapter;


public class QuranActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private int pageNumber;
    private int receivedPageNumber;
    private int pagesTurned;

    private String currentOrientation;

    private ViewPager2 pager;

    private ViewPager2.OnPageChangeCallback singlePageChangeCallback;
    private ViewPager2.OnPageChangeCallback dualPageChangeCallback;


    private QuranPageAdapter pagerAdapter;

    private String mushafVersion;
    boolean isSmoothVolumeKeyNavigation;
    boolean isForceDualPage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);

        mushafVersion = QuranSettings.getInstance().getMushafVersion(this);

        isSmoothVolumeKeyNavigation = QuranSettings.getInstance().getIsSmoothKeyNavigation(this);
        isForceDualPage = QuranSettings.getInstance().getIsForceDualPage(this);

        setupActionbar();

        Intent activityThatCalled = getIntent();

        receivedPageNumber = activityThatCalled.getIntExtra("new page number", 2);

        setupCurrentPage(savedInstanceState);

        currentOrientation = getScreenOrientation();

        setupPagers();

        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        actionOnSystemUIChange(visibility);
                    }
                });

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
            pager.unregisterOnPageChangeCallback(dualPageChangeCallback);
            destroyPager();
            setupSinglePager();
            pager.setCurrentItem(pageNumber - 1, false);

            pager.registerOnPageChangeCallback(singlePageChangeCallback);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            pager.unregisterOnPageChangeCallback(singlePageChangeCallback);
            destroyPager();
            final int dualPosition = setupDualPager();

            if (isForceDualPage)
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            pager.setCurrentItem(dualPosition, false);

            pager.registerOnPageChangeCallback(dualPageChangeCallback);
        }
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
    public void onBackPressed() {
        finish();
    }



    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int dualPageNumber;
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN && currentOrientation == "landscape" && pageNumber >= 2) {
                    pageNumber--;

                    if (pageNumber != 1)
                        pageNumber--;

                    if (pageNumber % 2 == 0)
                        dualPageNumber = pageNumber / 2;
                    else
                        dualPageNumber = (pageNumber - 1) / 2;

                    pager.setCurrentItem(dualPageNumber, isSmoothVolumeKeyNavigation);
                    return true;
                } else
                    return super.dispatchKeyEvent(event);

            case KeyEvent.KEYCODE_VOLUME_UP:
                if (!mushafVersion.equals("madani_15_line")) {
                    if (action == KeyEvent.ACTION_DOWN && currentOrientation == "landscape" && pageNumber <= 847) {
                        pageNumber++;

                        if (pageNumber != 848)
                            pageNumber++;

                        if (pageNumber % 2 == 0)
                            dualPageNumber = pageNumber / 2;
                        else
                            dualPageNumber = (pageNumber - 1) / 2;

                        pager.setCurrentItem(dualPageNumber, isSmoothVolumeKeyNavigation);
                        return true;
                    } else
                        return super.dispatchKeyEvent(event);
                } else {
                    if (action == KeyEvent.ACTION_DOWN && currentOrientation == "landscape" && pageNumber <= 602) {
                        pageNumber++;

                        if (pageNumber != 603)
                            pageNumber++;

                        if (pageNumber % 2 == 0)
                            dualPageNumber = (pageNumber - 1) / 2;
                        else
                            dualPageNumber = pageNumber / 2;

                        pager.setCurrentItem(dualPageNumber, isSmoothVolumeKeyNavigation);
                        return true;
                    } else
                        return super.dispatchKeyEvent(event);
                }
            default:
                return super.dispatchKeyEvent(event);
        }
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




    private void setupCurrentPage(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            pageNumber = receivedPageNumber;
            pagesTurned = 0;
        } else {
            pagesTurned = savedInstanceState.getInt("pagesTurned");
            pageNumber = savedInstanceState.getInt("currentPage");
        }
    }



    private void setupPagers() {
        singlePageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position != 0) {
                    pageNumber = position + 1;
                    pagesTurned++;
                    if (pagesTurned > 5) {
                        pagerAdapter.removeAllButLast();
                        pagesTurned = 0;
                    }
                }
            }};

        dualPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position != 0) {
                    pageNumber = 2*position;
                    pagesTurned = pagesTurned + 2;
                    if (pagesTurned > 5) {
                        pagerAdapter.removeAllButLast();
                        pagesTurned = 0;
                    }
                }
            }
        };

        if (currentOrientation.equals("portrait") && !isForceDualPage) {
            setupSinglePager();

            pager.setCurrentItem(pageNumber - 1, false);

            pager.registerOnPageChangeCallback(singlePageChangeCallback);

        } else {
            int dualPageNumber = setupDualPager();

            if (isForceDualPage)
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


            pager.setCurrentItem(dualPageNumber, false);

            pager.registerOnPageChangeCallback(dualPageChangeCallback);
        }
    }

    private void setupSinglePager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(), getLifecycle(), this, "portrait");
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
    }

    private int setupDualPager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(), getLifecycle(), this, "landscape");
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        return singlePageToDualPageNumber(pageNumber);
    }


    private void destroyPager() {
        pagerAdapter.removeAllfragments();
        pagerAdapter = null;
        pager = null;
        System.gc();
    }

    private int singlePageToDualPageNumber(int pageNumber) {
        int dualPageNumber;
        if (!mushafVersion.equals("madani_15_line")) {
            if (pageNumber % 2 == 0)
                dualPageNumber = pageNumber / 2;
            else
                dualPageNumber = (pageNumber - 1) / 2;
        } else {
            if (pageNumber % 2 == 0)
                dualPageNumber = pageNumber / 2 - 1;
            else
                dualPageNumber = (pageNumber - 1) / 2;
        }

        return dualPageNumber;
    }


    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private String getScreenOrientation() {
        final int screenOrientation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (screenOrientation) {
            case Surface.ROTATION_0:
                return "portrait";
            case Surface.ROTATION_90:
                return "landscape";
            case Surface.ROTATION_180:
                return "portrait";
            case Surface.ROTATION_270:
                return "landscape";
            default:
                return "landscape";
        }
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

}
