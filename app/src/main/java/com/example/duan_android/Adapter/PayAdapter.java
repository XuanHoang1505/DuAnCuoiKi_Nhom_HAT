package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_android.Model.Pay;
import com.example.duan_android.Model.mygift;
import com.example.duan_android.R;

import java.util.List;

public class PayAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Pay> payList;

    public PayAdapter(Context context, int layout, List<Pay> payList) {
        this.context = context;
        this.layout = layout;
        this.payList = payList;
    }

    @Override
    public int getCount() {
        return payList.size();
    }

    @Override
    public Object getItem(int i) {
        return payList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        Pay pay=payList.get(i);
        ImageView img = view.findViewById(R.id.img_pay);
        TextView title =view.findViewById(R.id.pay_title);

        img.setImageResource(pay.getResourceImage());
        title.setText(pay.getPayTitle());
        return view;
    }
}
