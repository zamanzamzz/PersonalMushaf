 package com.example.personalmushaf;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;


import com.bumptech.glide.Glide;

import com.example.personalmushaf.navigation.ThirteenLinePageData;
import com.example.personalmushaf.navigation.snappositionchangelistener.OnSnapPositionChangeListener;
import com.example.personalmushaf.navigation.snappositionchangelistener.RecyclerViewExtKt;
import com.example.personalmushaf.navigation.snappositionchangelistener.SnapOnScrollListener;
import com.example.personalmushaf.thirteenlinepage.ThirteenLineAdapter;
import com.example.personalmushaf.thirteenlinepage.ThirteenLineDualAdapter;


 public class QuranActivity extends AppCompatActivity {

     private Toolbar toolbar;

     private int pageNumber;
     private int receivedPageNumber;
     private int pagesTurned;

     private LinearLayoutManager layoutManager;
     private String currentOrientation;

     private RecyclerView singlePageRecyclerView;
     private RecyclerView dualPageRecyclerView;
     private ThirteenLineAdapter singlePageAdapter;
     private ThirteenLineDualAdapter dualPageAdapter;


     @Override
	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran);

        setupActionbar();

        Intent activityThatCalled = getIntent();

        receivedPageNumber = activityThatCalled.getIntExtra("new page number", 2);

        setupCurrentPage(savedInstanceState);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        currentOrientation = getScreenOrientation();

         if (currentOrientation.equals("portrait")) {
             setupSinglePageRecyclerView(layoutManager);
         }
        else {
            setupDualPageRecyclerView(layoutManager);
         }

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
		 final Context context = this;

		 if (id == android.R.id.home) {

             Glide.get(this).clearMemory();

             AsyncTask.execute(new Runnable() {
                 @Override
                 public void run() {
                     Glide.get(context).clearDiskCache();
                 }
             });

			 finish();

			 return true;
		 }

		 return super.onOptionsItemSelected(item);
	 }

     @Override
     public void onBackPressed() {
         final Context context = this;

         Glide.get(this).clearMemory();

         AsyncTask.execute(new Runnable() {
             @Override
             public void run() {
                 Glide.get(context).clearDiskCache();
             }
         });

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
                         dualPageNumber = pageNumber/2;
                     else
                         dualPageNumber = (pageNumber - 1)/2;
                     layoutManager.smoothScrollToPosition(dualPageRecyclerView, new RecyclerView.State(), dualPageNumber);
                     return true;
                 } else
                     return super.dispatchKeyEvent(event);

             case KeyEvent.KEYCODE_VOLUME_UP:
                 if (action == KeyEvent.ACTION_DOWN && currentOrientation == "landscape" && pageNumber <= 847) {
                     pageNumber++;

                     if (pageNumber != 848)
                         pageNumber++;

                     if (pageNumber % 2 == 0)
                         dualPageNumber = pageNumber/2;
                     else
                         dualPageNumber = (pageNumber - 1)/2;
                     layoutManager.smoothScrollToPosition(dualPageRecyclerView, new RecyclerView.State(), dualPageNumber);
                     return true;
                 }
                 else
                     return super.dispatchKeyEvent(event);
             default:
                 return super.dispatchKeyEvent(event);
         }
     }

     private void setupActionbar() {
         toolbar = findViewById(R.id.toolbar);
         ViewGroup.LayoutParams params = toolbar.getLayoutParams();
         params.height = params.height + getStatusBarHeight();
         toolbar.setPadding(0,getStatusBarHeight(),0,0);

         setSupportActionBar(toolbar);
         ActionBar actionBar = getSupportActionBar();

         actionBar.setDisplayHomeAsUpEnabled(true);
         actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);
         actionBar.setDisplayShowTitleEnabled(false);
         actionBar.setHomeButtonEnabled(true);
         actionBar.hide();
     }

     private int getStatusBarHeight() {
         int result = 0;
         int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
         if (resourceId > 0) {
             result = getResources().getDimensionPixelSize(resourceId);
         }
         return result;
     }

     private void setupCurrentPage(Bundle savedInstanceState) {
         if (savedInstanceState == null) {
             pageNumber = receivedPageNumber;
             pagesTurned = 0;
         }
         else {
             pagesTurned = savedInstanceState.getInt("pagesTurned");
             pageNumber = savedInstanceState.getInt("currentPage");
         }
     }

     private String getScreenOrientation(){
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

    private void setupSinglePageRecyclerView(RecyclerView.LayoutManager layoutManager) {
        singlePageRecyclerView = findViewById(R.id.pager);
        singlePageRecyclerView.setHasFixedSize(true);
        singlePageAdapter = new ThirteenLineAdapter(ThirteenLinePageData.singlePageSets);
        singlePageRecyclerView.setLayoutManager(layoutManager);
        layoutManager.scrollToPosition(pageNumber-1);
        layoutManager.setItemPrefetchEnabled(true);
        singlePageRecyclerView.setAdapter(singlePageAdapter);
        singlePageRecyclerView.setItemViewCacheSize(3);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        RecyclerViewExtKt.attachSnapHelperWithListener(singlePageRecyclerView, snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, new OnSnapPositionChangeListener() {
            @Override
            public void onSnapPositionChange(int position) {
                pageNumber = position + 1;
                pagesTurned++;
            }
        });
    }

    private void setupDualPageRecyclerView(RecyclerView.LayoutManager layoutManager) {
        int dualPageNumber;
        if (pageNumber % 2 == 0)
            dualPageNumber = pageNumber/2;
        else
            dualPageNumber = (pageNumber - 1)/2;

        dualPageRecyclerView = findViewById(R.id.dualpager);
        dualPageRecyclerView.setHasFixedSize(true);
        dualPageAdapter = new ThirteenLineDualAdapter(ThirteenLinePageData.dualPageSets);
        dualPageRecyclerView.setLayoutManager(layoutManager);
        layoutManager.scrollToPosition(dualPageNumber);
        layoutManager.setItemPrefetchEnabled(true);
        dualPageRecyclerView.setAdapter(dualPageAdapter);
        dualPageRecyclerView.setItemViewCacheSize(3);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        RecyclerViewExtKt.attachSnapHelperWithListener(dualPageRecyclerView, snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, new OnSnapPositionChangeListener() {
            @Override
            public void onSnapPositionChange(int position) {
                pageNumber = 2*position;
                pagesTurned = pagesTurned + 2;
            }
        });
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
