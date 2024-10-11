package com.example.duan_android.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.duan_android.Adapter.MovieViewPagerAdapter;
import com.example.duan_android.R;
import com.example.duan_android.Widget.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

public class ViewMoreActivity extends AppCompatActivity {
    private TabLayout VM_tabLayout;
    private CustomViewPager VM_viewPager;
    private ImageView imageViewBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_more);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        VM_tabLayout = findViewById(R.id.vm_tab_layout);
        VM_viewPager = findViewById(R.id.vm_movie_viewpager);
        imageViewBack = findViewById(R.id.img_view_back);

        MovieViewPagerAdapter viewPagerAdapter= new MovieViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,true,false);
        VM_viewPager.setAdapter(viewPagerAdapter);
        VM_viewPager.setPagingEnable(false);
        VM_tabLayout.setupWithViewPager(VM_viewPager);

        int selectedTab = getIntent().getIntExtra("selected_tab", 0); // Mặc định là 0 (tab "Đang chiếu")
        VM_viewPager.setCurrentItem(selectedTab);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}