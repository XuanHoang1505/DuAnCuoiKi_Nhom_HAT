package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.duan_android.Model.cinema;
import com.example.duan_android.R;

import java.util.List;

public class AdapterCinema extends BaseAdapter {
    private Context context;
    private int layout;
    private List<cinema> cinemaList;
    private OnItemClickListener mListener;

    public AdapterCinema(Context context, int layout, List<cinema> cinemaList, OnItemClickListener listener) {
        this.context = context;
        this.layout = layout;
        this.cinemaList = cinemaList;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return cinemaList.size();
    }

    @Override
    public Object getItem(int position) {
        return cinemaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
        }

        ImageView imgCinema = view.findViewById(R.id.hinhanh_cinema);
        TextView tvName = view.findViewById(R.id.ten_cinema);
        TextView sdt = view.findViewById(R.id.sdt_cinema);
        TextView address = view.findViewById(R.id.diachi_cinema);
        final cinema cinemaItem = cinemaList.get(position);

        imgCinema.setImageResource(cinemaItem.getHinh());
        tvName.setText(cinemaItem.getTenrap());
        sdt.setText(cinemaItem.getSdt());
        address.setText(cinemaItem.getDiachi());
        // Gắn sự kiện click cho mỗi item
        view.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(cinemaItem); // Truyền đối tượng cinema khi click
            }
        });

        return view;
    }

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(cinema cinema); // Truyền đối tượng cinema khi item được click
    }
}
