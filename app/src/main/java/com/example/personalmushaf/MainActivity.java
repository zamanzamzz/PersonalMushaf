 package com.example.personalmushaf;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.personalmushaf.navigation.NavigationActivity;
import com.example.personalmushaf.thirteenlinepage.ThirteenLinePagerAdapter;

 public class MainActivity extends AppCompatActivity {


     private ViewPager pager;
     private FragmentPagerAdapter adapter;
     private Toolbar toolbar;
     private Button toolbarButton;


     @Override

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarButton = findViewById(R.id.toolbar_button);
        toolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNavigation = new Intent(getBaseContext(), NavigationActivity.class);

                startActivity(goToNavigation);
            }
        });
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

}
