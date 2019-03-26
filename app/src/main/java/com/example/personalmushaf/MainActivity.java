 package com.example.personalmushaf;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.personalmushaf.navigationlistview.NavigationActivity;
import com.example.personalmushaf.thirteenlinepage.ThirteenLinePagerAdapter;

 public class MainActivity extends AppCompatActivity {


     private ViewPager pager;
     private FragmentPagerAdapter adapter;
     private Toolbar toolbar;
     private Button button;


     @Override

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.toolbar_button);
        setSupportActionBar(toolbar);
        pager = findViewById(R.id.pager);
        adapter = new ThirteenLinePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount()-2, false);
        pager.setOffscreenPageLimit(5);
    }

    public  void hideActionBar(View view) {
         ActionBar actionBar = getSupportActionBar();
         if (actionBar.isShowing())
             actionBar.hide();
         else
             actionBar.show();
    }

    public void startNavigationActivity(View view) {
        Intent intent = new Intent(this, NavigationActivity.class);

        startActivity(intent);
    }



}
