package com.example.duan_android.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duan_android.Fragment.FirstDateFragment;
import com.example.duan_android.Model.NgayChieu;

import java.util.ArrayList;
import java.util.List;

public class DateCinemaAdapter extends FragmentStatePagerAdapter {
    private List<NgayChieu> tabItems = new ArrayList<>();

    public DateCinemaAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    // Cập nhật danh sách ngày chiếu
    public void setTabItems(List<NgayChieu> tabItems) {
        this.tabItems = tabItems;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        String ngaychieu = tabItems.get(position).getNgaychieu();

        FirstDateFragment fragment = new FirstDateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("NgayChieu", ngaychieu);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return tabItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabItems.get(position).getNgaychieu();
    }
}
