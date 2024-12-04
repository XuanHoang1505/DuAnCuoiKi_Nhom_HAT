package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_android.Model.deal;
import com.example.duan_android.R;

import java.util.List;

public class DealAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<deal> arraylist;

    // Constructor
    public DealAdapter(Context context, int layout, List<deal> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size(); // Trả về số lượng phần tử trong danh sách
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position); // Trả về đối tượng deal tại vị trí position
    }

    @Override
    public long getItemId(int position) {
        return position; // Trả về ID của item (thường là vị trí của item trong danh sách)
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);
        deal dl = arraylist.get(position);

        ImageView img = convertView.findViewById(R.id.img);
        TextView name = convertView.findViewById(R.id.namemovie);
        TextView room = convertView.findViewById(R.id.roomcinema);
        TextView cinema = convertView.findViewById(R.id.namecinema);
        TextView time = convertView.findViewById(R.id.timemovie);
        TextView date = convertView.findViewById(R.id.datemovie);
        img.setImageResource(dl.getHinhanh());
        name.setText(dl.getTenphim());
        room.setText(dl.getPhongchieu());
        cinema.setText(dl.getRapphim());
        time.setText(dl.getGiobatdau());
        date.setText(dl.getNgaychieu());
        return convertView;
    }


}