package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.duan_android.Model.combo;
import com.example.duan_android.R;

import java.util.List;

public class AdapterCombo extends BaseAdapter {

    private Context context;
    private int layout;
    private List<combo> arraylist;

    public AdapterCombo(Context context, int layout, List<combo> arraylist) {
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
        combo cb=arraylist.get(i);
        ImageView img=view.findViewById(R.id.combo_image);
        TextView title=view.findViewById(R.id.combo_title);
        TextView info=view.findViewById(R.id.combo_information);
        TextView price=view.findViewById(R.id.combo_price);

        img.setImageResource(cb.getImage());
        title.setText(cb.getTitle());
        info.setText(cb.getInfo());
        price.setText(String.valueOf(cb.getPrice()));
        return view;
    }
}
