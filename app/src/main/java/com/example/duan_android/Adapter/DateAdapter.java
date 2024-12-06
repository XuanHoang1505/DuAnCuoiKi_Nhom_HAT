package com.example.duan_android.Adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.duan_android.Fragment.Date1Fragment;
import com.example.duan_android.Model.NgayChieu;

import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends FragmentStatePagerAdapter {
    private List<NgayChieu> tabItems=new ArrayList<>();

    public DateAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setTabItems(List<NgayChieu> tabItems) {
        this.tabItems = tabItems;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        String ngaychieu = tabItems.get(position).getNgaychieu();

        // Khởi tạo Date1Fragment và truyền IDGioChieu vào qua Bundle
        Date1Fragment fragment = new Date1Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("NgayChieu", ngaychieu);
        fragment.setArguments(bundle);  // Truyền dữ liệu vào Fragment thông qua setArguments

        return fragment;  // Trả về fragment với dữ liệu đã được truyền
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