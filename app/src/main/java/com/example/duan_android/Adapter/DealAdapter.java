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

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder = new ViewHolder();
            viewHolder.img = convertView.findViewById(R.id.img);
            viewHolder.name = convertView.findViewById(R.id.namemovie);
            viewHolder.room = convertView.findViewById(R.id.roomcinema);
            viewHolder.cinema = convertView.findViewById(R.id.namecinema);
            viewHolder.time = convertView.findViewById(R.id.timemovie);
            viewHolder.date = convertView.findViewById(R.id.datemovie);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        deal dl = arraylist.get(position);

        // Cập nhật các trường thông tin của deal
        viewHolder.img.setImageResource(dl.getHinhanh());
        viewHolder.name.setText(dl.getTenphim());
        viewHolder.room.setText(dl.getPhongchieu()); // Gán giá trị cho trường phòng chiếu
        viewHolder.cinema.setText(dl.getRapphim());
        viewHolder.time.setText(dl.getGiobatdau());
        viewHolder.date.setText(dl.getNgaychieu());

        return convertView;
    }

    // Lớp ViewHolder để giữ các thành phần view của mỗi item trong ListView
    static class ViewHolder {
        ImageView img;
        TextView name;
        TextView room;
        TextView cinema;
        TextView time;
        TextView date;
    }
}