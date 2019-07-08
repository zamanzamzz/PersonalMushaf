package com.example.personalmushaf;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.personalmushaf.quranpage.QuranPageAdapter;


public class QuranActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private int pageNumber;
    private int receivedPageNumber;
    private int pagesTurned;

    private String currentOrientation;

    private ViewPager pager;

    private QuranPageAdapter pagerAdapter;


    private SharedPreferences preferences;
    String currentMushaf;
    boolean isSmoothVolumeKeyNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        currentMushaf = preferences.getString("mushaf", "madani_15_line");

        isSmoothVolumeKeyNavigation = preferences.getBoolean("smoothpageturn", false);

        setupActionbar();

        Intent activityThatCalled = getIntent();

        receivedPageNumber = activityThatCalled.getIntExtra("new page number", 2);

        setupCurrentPage(savedInstanceState);

        currentOrientation = getScreenOrientation();

        setupPager();

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



    private void setupPager() {
        pager = findViewById(R.id.pager);
        pagerAdapter = new QuranPageAdapter(getSupportFragmentManager(), this, currentOrientation);
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(2);

        if (currentOrientation.equals("portrait")) {
            final int position = pagerAdapter.getCount() - pageNumber;
            pager.post(new Runnable() {
                @Override
                public void run() {
                    pager.setCurrentItem(position, false);
                }
            });
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    pageNumber = pagerAdapter.getCount() - position;
                    pagesTurned++;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {
            int dualPageNumber;

            if (!currentMushaf.equals("madani_15_line")) {
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

            final int position = pagerAdapter.getCount() - 1 - dualPageNumber;
            pager.post(new Runnable() {
                @Override
                public void run() {
                    pager.setCurrentItem(position, false);
                }
            });
            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (currentMushaf.equals("madani_15_line"))
                        pageNumber = 604 - 2 * position + 1;
                    else
                        pageNumber = 848 - 2 * position;
                    pagesTurned = pagesTurned + 2;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
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
