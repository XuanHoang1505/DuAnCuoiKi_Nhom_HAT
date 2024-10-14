package com.example.duan_android.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duan_android.Adapter.GiftViewPagerAdpater;
import com.example.duan_android.R;
import com.google.android.material.tabs.TabLayout;

public class GiftActivity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager viewPager;
    private GiftViewPagerAdpater giftViewPagerAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift);
        tab=findViewById(R.id.tabgift);
        viewPager=findViewById(R.id.viewpagergift);

        giftViewPagerAdpater=new GiftViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(giftViewPagerAdpater);
        tab.setupWithViewPager(viewPager);
    }
}
