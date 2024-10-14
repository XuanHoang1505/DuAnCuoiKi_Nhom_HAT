package com.example.duan_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.duan_android.Activity.BookingActivty;
import com.example.duan_android.Model.lichchieu;

import com.example.duan_android.R;

import java.util.List;

public class AdapterLichChieu extends BaseAdapter {

    private Context context;
    private int layout;
    private List<lichchieu> arraylist;

    public AdapterLichChieu(Context context, int layout, List<lichchieu> arraylist) {
        this.context = context;
        this.layout = layout;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return arraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        lichchieu lc = arraylist.get(i);

        TextView tenrap = view.findViewById(R.id.txttenrap);
        GridLayout layoutShowtimes = view.findViewById(R.id.giochieu);
        tenrap.setText(lc.getTenrp());

        for (String time : lc.getGiochieu()) {
            Button btntime = new Button(context);
            btntime.setText(time);
            btntime.setPadding(16, 8, 16, 8);

            btntime.setBackgroundResource(R.drawable.btn_giochieu);

            btntime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BookingActivty.class);
                    context.startActivity(intent);
                }
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(8, 8, 8, 8);

            btntime.setLayoutParams(params);
            layoutShowtimes.addView(btntime);
        }
        return view;
    }


}
