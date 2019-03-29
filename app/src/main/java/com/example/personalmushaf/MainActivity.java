 package com.example.personalmushaf;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;

import android.view.WindowManager;


import com.example.personalmushaf.navigation.NavigationActivity;

import com.example.personalmushaf.navigation.QuranPageData;
import com.example.personalmushaf.thirteenlinepage.PageLayoutManager;
import com.example.personalmushaf.thirteenlinepage.RecyclerViewExtKt;
import com.example.personalmushaf.thirteenlinepage.ThirteenLineAdapter;
import com.example.personalmushaf.thirteenlinepage.ThirteenLineDualAdapter;


 public class MainActivity extends AppCompatActivity {


	 private RecyclerView pager;
	 private RecyclerView dualPager;
	 private ThirteenLineAdapter adapter;
	 private ThirteenLineDualAdapter dualAdapter;
	 private Toolbar toolbar;
	 private String currentOrientation;
	 private int pageNumber;
	 private int receivedPageNumber;


	 @Override

	 protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);

        Intent activityThatCalled = getIntent();


        PageLayoutManager layoutManager = new PageLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);

        String from = activityThatCalled.getStringExtra("from");

        currentOrientation = getScreenOrientation(this);

        receivedPageNumber = activityThatCalled.getIntExtra("new page number", 2);

        if (from != null) {
            if (savedInstanceState == null)
                pageNumber = receivedPageNumber;
            else if (receivedPageNumber != savedInstanceState.getInt("currentPage"))
                pageNumber = savedInstanceState.getInt("currentPage");
        }
        else if (pageNumber == 0 && savedInstanceState != null)
            pageNumber = savedInstanceState.getInt("currentPage");
        else
            pageNumber = 2;


         if (currentOrientation.equals("portrait")) {
             pager = findViewById(R.id.pager);
             pager.setHasFixedSize(true);
             adapter = new ThirteenLineAdapter(QuranPageData.getInstance().singlePageSets);
             pager.setLayoutManager(layoutManager);
             layoutManager.scrollToPosition(pageNumber-1);
             layoutManager.setItemPrefetchEnabled(true);
             pager.setAdapter(adapter);
             pager.setItemViewCacheSize(10);
             PagerSnapHelper snapHelper = new PagerSnapHelper();
             RecyclerViewExtKt.attachSnapHelperWithListener(pager, snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, new OnSnapPositionChangeListener() {
                 @Override
                 public void onSnapPositionChange(int position) {
                     pageNumber = position + 1;
                 }
             });
         }
        else {
            int dualPageNumber;
            if (pageNumber % 2 == 0)
                dualPageNumber = pageNumber/2;
            else
                dualPageNumber = (pageNumber - 1)/2;

             dualPager = findViewById(R.id.dualpager);
             dualPager.setHasFixedSize(true);
             dualAdapter = new ThirteenLineDualAdapter(QuranPageData.getInstance().dualPageSets);
             dualPager.setLayoutManager(layoutManager);
             layoutManager.scrollToPosition(dualPageNumber);
             layoutManager.setItemPrefetchEnabled(true);
             dualPager.setAdapter(dualAdapter);
             dualPager.setItemViewCacheSize(10);
             PagerSnapHelper snapHelper = new PagerSnapHelper();
             RecyclerViewExtKt.attachSnapHelperWithListener(dualPager, snapHelper, SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL_STATE_IDLE, new OnSnapPositionChangeListener() {
                 @Override
                 public void onSnapPositionChange(int position) {
                     pageNumber = position + 1;
                 }
             });
         }
	}

     @Override
     public void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
         outState.putInt("currentPage", pageNumber);
     }

     public  void hideActionBar(View view) {
		 ActionBar actionBar = getSupportActionBar();
		 if (actionBar.isShowing())
			 actionBar.hide();
		 else
			 actionBar.show();
	}

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 int id = item.getItemId();

		 if (id == android.R.id.home) {

			 Intent goToNavigation = new Intent(getBaseContext(), NavigationActivity.class);

			 startActivity(goToNavigation);

			 return true;
		 }

		 return super.onOptionsItemSelected(item);
	 }

	 public String getScreenOrientation(Context context){
		 final int screenOrientation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
		 switch (screenOrientation) {
			 case Surface.ROTATION_0:
				 return "portrait";
			 case Surface.ROTATION_90:
				 return "landscape";
			 case Surface.ROTATION_180:
				 return "portrait";
			 default:
				 return "landscape";
		 }
	 }


}
