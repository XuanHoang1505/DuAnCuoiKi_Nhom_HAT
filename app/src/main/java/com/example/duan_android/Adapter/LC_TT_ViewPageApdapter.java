package com.example.duan_android.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duan_android.Fragment.LichChieuFragment;
import com.example.duan_android.Fragment.ThongTinFragment;

public class LC_TT_ViewPageApdapter extends FragmentStatePagerAdapter {
    public LC_TT_ViewPageApdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LichChieuFragment();
            case 1:
                return new ThongTinFragment();
            default:
                return new LichChieuFragment();
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
                return "Lịch Chiếu";
            case 1:
                return "Thông Tin";
            default:
                return "Lịch Chiếu";
        }
    }
}
