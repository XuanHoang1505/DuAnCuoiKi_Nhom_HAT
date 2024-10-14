package com.example.duan_android.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duan_android.Fragment.Gift1Fragment;
import com.example.duan_android.Fragment.Gift2Fragment;
import com.example.duan_android.Fragment.Gift3Fragment;

public class GiftViewPagerAdpater extends FragmentStatePagerAdapter {
    public GiftViewPagerAdpater(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Gift1Fragment();
            case 1:
                return new Gift2Fragment();
            case 2:
                return new Gift3Fragment();
            default:
                return new Gift1Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Đổi quà";
            case 1:
                return "Quà của tôi";
            default:
                return "Đã dùng";
        }
    }
}
