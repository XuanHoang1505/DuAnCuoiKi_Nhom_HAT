package com.example.duan_android.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_android.Model.gift;
import com.example.duan_android.R;

import java.util.List;

public class AdapterGift extends BaseAdapter {
    private Context context;
    private int layout;
    private List<gift> arraylist;

    public AdapterGift(Context context, int layout, List<gift> arraylist) {
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
        gift gf=arraylist.get(i);
        ImageView img=view.findViewById(R.id.imggift);
        TextView name=view.findViewById(R.id.namegift);
        TextView point=view.findViewById(R.id.pointgift);

        img.setImageResource(gf.getImggift());
        name.setText(gf.getNamegift());
        point.setText(String.valueOf(gf.getPoint()));

        Button btnchange=view.findViewById(R.id.btn_change);
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }

    private void showDialog() {
        Dialog dialog=new Dialog(context);
        dialog.setContentView((R.layout.dialog_change));
        Button btnclose=dialog.findViewById(R.id.btn_close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
