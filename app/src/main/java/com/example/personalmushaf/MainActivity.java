 package com.example.personalmushaf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.personalmushaf.thirteenlinepage.ThirteenLinePagerAdapter;

 public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        adapter = new ThirteenLinePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount()-2, false);
        pager.setOffscreenPageLimit(5);
    }
}
