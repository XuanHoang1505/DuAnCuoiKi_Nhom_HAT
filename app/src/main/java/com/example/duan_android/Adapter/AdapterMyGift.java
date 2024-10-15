package com.example.duan_android.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_android.Model.mygift;
import com.example.duan_android.R;

import java.util.List;

public class AdapterMyGift extends BaseAdapter {
    private Context context;
    private int layout;
    private List<mygift> arraylist;

    public AdapterMyGift(Context context, int layout, List<mygift> arraylist) {
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
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(layout,null);
        mygift gf=arraylist.get(i);
        ImageView img=view.findViewById(R.id.imggift);
        TextView name=view.findViewById(R.id.namegift);
        TextView quan=view.findViewById(R.id.quantitygift);

        img.setImageResource(gf.getImggift());
        name.setText(gf.getNamegift());
        quan.setText(String.valueOf(gf.getQuantity()));
        return view;
    }
}
