package com.example.duan_android.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.duan_android.Fragment.ComingSoonFragment;
import com.example.duan_android.Fragment.ShowingFragment;
import com.example.duan_android.Fragment.ViewMoreComingSoonFragment;
import com.example.duan_android.Fragment.ViewMoreShowingFragment;

public class MovieViewPagerAdapter extends FragmentPagerAdapter {
    private boolean isForViewMore;

    public MovieViewPagerAdapter(@NonNull FragmentManager fm, int behavior, boolean isForViewMore) {
        super(fm, behavior);
        this.isForViewMore = isForViewMore;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (isForViewMore) {
            // Fragment cho ViewMoreActivity
            switch (position) {
                case 0:
                    return new ViewMoreShowingFragment(); // Fragment riêng cho ViewMoreActivity
                case 1:
                    return new ViewMoreComingSoonFragment();
                default:
                    return new ViewMoreShowingFragment();
            }
        } else {
            // Fragment cho HomeFragment
            switch (position) {
                case 0:
                    return new ShowingFragment();
                case 1:
                    return new ComingSoonFragment();
                default:
                    return new ShowingFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Đang chiếu";
            case 1:
                return "Sắp chiếu";
            default:
                return "Đang chiếu";
        }
    }
}
