package com.example.duan_android.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.duan_android.Fragment.ComingSoonFragment;
import com.example.duan_android.Fragment.ShowingFragment;

public class MovieViewPagerAdapter extends FragmentPagerAdapter {

    public MovieViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
         switch (position){
             case 0:
                 return new ShowingFragment();
             case 1:
                 return new ComingSoonFragment();
         }
         return new ShowingFragment();
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
