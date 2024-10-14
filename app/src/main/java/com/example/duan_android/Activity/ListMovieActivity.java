package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duan_android.Adapter.DateAdapter;
import com.example.duan_android.R;
import com.google.android.material.tabs.TabLayout;

public class ListMovieActivity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager viewPager;
    private ImageView btn_cinemaBack;
    private DateAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tab = findViewById(R.id.tab_dateShow);
        viewPager = findViewById(R.id.viewPagerMovieList);
        btn_cinemaBack = findViewById(R.id.btn_cinemaBack);
        viewPager = findViewById(R.id.viewPagerMovieList);

        btn_cinemaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new DateAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, true);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab tablayout_date = tab.getTabAt(i);
            if (tablayout_date != null) {
                View customView = getLayoutInflater().inflate(R.layout.custom_tab, null);
                TextView tabTitle = customView.findViewById(R.id.tab_title);
                TextView tabDate = customView.findViewById(R.id.tab_date);

                switch (i) {
                    case 0:
                        tabTitle.setText("Thứ 4");
                        tabDate.setText("25/09");
                        break;
                    case 1:
                        tabTitle.setText("Thứ 5");
                        tabDate.setText("26/09");
                        break;
                    case 2:
                        tabTitle.setText("Thứ 6");
                        tabDate.setText("27/09");
                        break;
                    case 3:
                        tabTitle.setText("Thứ 7");
                        tabDate.setText("28/09");
                        break;
                }
                tablayout_date.setCustomView(customView);
            }
        }

    }
}