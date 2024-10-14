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

    public DealAdapter(Context context, int layout, List<deal> arraylist) {
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
        deal dl=arraylist.get(i);
        ImageView img=view.findViewById(R.id.img);
        TextView name=view.findViewById(R.id.namemovie);
        TextView type=view.findViewById(R.id.movietype);
        TextView code=view.findViewById(R.id.moviecode);
        TextView room=view.findViewById(R.id.roomcinema);
        TextView cinema=view.findViewById(R.id.namecinema);
        TextView time=view.findViewById(R.id.timemovie);
        TextView date=view.findViewById(R.id.datemovie);

        img.setImageResource(dl.getImg());
        name.setText(dl.getNamemovie());
        type.setText(dl.getType());
        code.setText(dl.getCode());
        room.setText(dl.getRoom());
        cinema.setText(dl.getNamecinema());
        time.setText(dl.getTime());
        date.setText(dl.getDate());

        return view;
    }
}
