package com.example.duan_android.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duan_android.Adapter.LC_TT_ViewPageApdapter;
import com.example.duan_android.R;
import com.google.android.material.tabs.TabLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class LC_TT_Activity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager viewPager;
    private LC_TT_ViewPageApdapter viewPageApdapter;
    private YouTubePlayerView trailler;
    private TextView txtMovieTitle;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lc_tt);
        tab=findViewById(R.id.tab);
        viewPager=findViewById(R.id.viewpage_lc_tt);
        trailler=findViewById(R.id.trallercinema);
        txtMovieTitle = findViewById(R.id.txtMovieTitle);
        getLifecycle().addObserver(trailler);

        txtMovieTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPageApdapter=new LC_TT_ViewPageApdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageApdapter);
        tab.setupWithViewPager(viewPager);
    }
}
