package com.example.personalmushaf;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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

        setPageNumberAndPagesTurned(savedInstanceState);

        currentOrientation = getScreenOrientation();

        setupInitialPager();

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
            destroyPager();
            currentOrientation = "portrait";
            setupSinglePager();

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            destroyPager();
            currentOrientation = "landscape";
            setupDualPager();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        destroyPager();
        finish();
    }



    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (currentOrientation.equals("landscape") && action == KeyEvent.ACTION_DOWN) {
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
        singlePageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageNumber = singlePagerPositionToPageNumber(position);
                pagesTurned++;
                if (pagesTurned > 7) {
                    pager.post(new Runnable() {
                        @Override
                        public void run() {
                            pagerAdapter.removeAllButLast();
                        }
                    });
                    pagesTurned = 0;
                }
            }};

        dualPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
            super.onPageSelected(position);
                pageNumber = dualPagerPositionToPageNumber(position);
                pagesTurned = pagesTurned + 2;
                if (pagesTurned > 7) {
                    pager.post(new Runnable() {
                        @Override
                        public void run() {
                            pagerAdapter.removeAllButLast();
                        }
                    });
                    pagesTurned = 0;
                }
            }
        };

        if (currentOrientation.equals("portrait") && !isForceDualPage) {
            setupSinglePager();
        } else {
            setupDualPager();
        }
    }

    private void setupSinglePager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(), getLifecycle(), this, "portrait");
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.setCurrentItem(pageNumberToSinglePagerPosition(pageNumber), false);
        pager.registerOnPageChangeCallback(singlePageChangeCallback);
    }

    private void setupDualPager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(), getLifecycle(), this, "landscape");
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(1);
        pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber), false);
        pager.registerOnPageChangeCallback(dualPageChangeCallback);
        if (isForceDualPage)
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    private void destroyPager() {
        if (currentOrientation.equals("landscape"))
            pager.unregisterOnPageChangeCallback(dualPageChangeCallback);
        else
            pager.unregisterOnPageChangeCallback(singlePageChangeCallback);
        pagerAdapter.removeAllfragments();
        pagerAdapter = null;
        pager = null;
        System.gc();
    }

    private int pageNumberToDualPagerPosition(int pageNumber) {
        if (!mushafVersion.equals("madani_15_line")) {
            if (pageNumber % 2 == 0)
                return pageNumber / 2 - 1;
            else
                return (pageNumber - 1) / 2 - 1;
        } else {
            if (pageNumber % 2 == 0)
                return pageNumber / 2 - 1;
            else
                return (pageNumber - 1) / 2;
        }
    }

    private int dualPagerPositionToPageNumber(int dualPageNumber) {
        if (!mushafVersion.equals("madani_15_line")) {
            return dualPageNumber*2 + 2;
        } else {
            return dualPageNumber*2 + 1;
        }
    }

    private int pageNumberToSinglePagerPosition(int pageNumber) {
        if (!mushafVersion.equals("madani_15_line")) {
            return pageNumber - 2;
        } else {
            return pageNumber - 1;
        }
    }

    private int singlePagerPositionToPageNumber(int position) {
        if (!mushafVersion.equals("madani_15_line")) {
            return position + 2;
        } else {
            return position + 1;
        }
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
        final int screenOrientation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
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

    private void vibrate(Vibrator v, int time) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(time, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(time);
        }
    }

    private void flipPageBackward(int pagesToFlip) {
        if (mushafVersion.equals("madani_15_line")) {
            if (pageNumber >= 2) {
                pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber - pagesToFlip), isSmoothVolumeKeyNavigation);
            }
        } else {
            if (pageNumber >= 3) {
                pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber - pagesToFlip), isSmoothVolumeKeyNavigation);
            }
        }
    }

    private void flipPageForward(int pagesToFlip) {
        if (mushafVersion.equals("madani_15_line")) {
            if (pageNumber <= 602) {
                pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber + pagesToFlip), isSmoothVolumeKeyNavigation);
            }
        } else {
            if (pageNumber <= 847) {
                pager.setCurrentItem(pageNumberToDualPagerPosition(pageNumber + pagesToFlip), isSmoothVolumeKeyNavigation);
            }
        }
    }

}
