package com.example.duan_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    private ImageView btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift);
        tab=findViewById(R.id.tabgift);
        viewPager=findViewById(R.id.viewpagergift);
        btn_close = findViewById(R.id.btn_close);

        giftViewPagerAdpater=new GiftViewPagerAdpater(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(giftViewPagerAdpater);
        tab.setupWithViewPager(viewPager);


        int selectedTab = getIntent().getIntExtra("selected_tab", 0);
        viewPager.setCurrentItem(selectedTab);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
