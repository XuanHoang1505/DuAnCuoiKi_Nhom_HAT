package com.example.duan_android.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duan_android.Fragment.Date1Fragment;
import com.example.duan_android.Fragment.Date2Fragment;
import com.example.duan_android.Fragment.Date3Fragment;
import com.example.duan_android.Fragment.Date4Fragment;

public class DateAdapter extends FragmentStatePagerAdapter {
    public DateAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Date1Fragment();
            case 1:
                return new Date2Fragment();
            case 2:
                return new Date3Fragment();
            case 3:
                return new Date4Fragment();
            default:
                return new Date1Fragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
