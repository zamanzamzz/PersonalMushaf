 package com.example.personalmushaf;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.personalmushaf.navigation.NavigationActivity;
import com.example.personalmushaf.navigation.QuranPageData;
import com.example.personalmushaf.thirteenlinepage.ThirteenLinePagerAdapter;



 public class MainActivity extends AppCompatActivity {


     private ViewPager pager;
     private FragmentPagerAdapter adapter;
     private Toolbar toolbar;


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
        pager = findViewById(R.id.pager);
        adapter = new ThirteenLinePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        pager.setCurrentItem(adapter.getCount()-activityThatCalled.getIntExtra("new page number", 2), false);

        pager.setOffscreenPageLimit(5);
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


}
