package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_android.Model.KhachHangVoucher;
import com.example.duan_android.R;

import java.util.List;

public class AdapterKhachHangVoucher extends BaseAdapter {
    private Context context;
    private int layout;
    private List<KhachHangVoucher> voucherList;

    public AdapterKhachHangVoucher(Context context, int layout, List<KhachHangVoucher> voucherList) {
        this.context = context;
        this.layout = layout;
        this.voucherList = voucherList;
    }

    @Override
    public int getCount() {
        return voucherList.size();
    }

    @Override
    public Object getItem(int i) {
        return voucherList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return voucherList.get(i).getIdVoucher();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        KhachHangVoucher voucher = voucherList.get(i);

        ImageView imgVoucher = view.findViewById(R.id.imggift);
        TextView txtTenVoucher = view.findViewById(R.id.namegift);
        TextView txtSoLuong = view.findViewById(R.id.quantitygift);

        imgVoucher.setImageResource(voucher.getHinhAnh());
        txtTenVoucher.setText(voucher.getTenVoucher());
        txtSoLuong.setText(String.valueOf(voucher.getSoLuong()));

        return view;
    }
}
