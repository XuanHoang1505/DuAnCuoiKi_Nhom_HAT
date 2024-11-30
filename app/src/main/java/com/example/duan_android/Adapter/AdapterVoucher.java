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

import com.example.duan_android.Model.Voucher;
import com.example.duan_android.R;
import java.util.List;

public class AdapterVoucher extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Voucher> voucherList;

    public AdapterVoucher(Context context, int layout, List<Voucher> voucherList) {
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
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        // Lấy dữ liệu từ danh sách
        Voucher voucher = voucherList.get(i);

        // Ánh xạ view
        ImageView imgVoucher = view.findViewById(R.id.imggift);
        TextView txtTenVoucher = view.findViewById(R.id.namegift);
        TextView txtSoDiemDoi = view.findViewById(R.id.pointgift);

        imgVoucher.setImageResource(voucher.getHinhAnh());
        txtTenVoucher.setText(voucher.getTenVoucher());
        txtSoDiemDoi.setText(String.valueOf(voucher.getSoDiemDoi()));
        Button btnChange = view.findViewById(R.id.btn_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        return view;
    }

    private void showDialog() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_change);

        Button btnClose = dialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
