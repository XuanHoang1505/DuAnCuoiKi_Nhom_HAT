package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_android.Model.MyVoucher; // Sửa import từ MyGift thành MyVoucher
import com.example.duan_android.R;

import java.util.List;

public class AdapterMyVoucher extends BaseAdapter {
    private Context context;
    private int layout;
    private List<MyVoucher> myVoucherList;
    private int selectedPosition = -1; // Vị trí được chọn (-1 là không chọn gì)

    public AdapterMyVoucher(Context context, int layout, List<MyVoucher> myVoucherList) {
        this.context = context;
        this.layout = layout;
        this.myVoucherList = myVoucherList;
    }

    @Override
    public int getCount() {
        return myVoucherList.size();
    }

    @Override
    public Object getItem(int position) {
        return myVoucherList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        MyVoucher myVoucher = myVoucherList.get(position);

        ImageView imgVoucher = convertView.findViewById(R.id.imgVoc);
        TextView txtVoucherName = convertView.findViewById(R.id.voucher_name);
        TextView txtDiscountAmount = convertView.findViewById(R.id.discount_amount);
        CheckBox checkBoxSelect = convertView.findViewById(R.id.checkbox_select);

        // Set dữ liệu
        imgVoucher.setImageResource(myVoucher.getImggift());
        txtVoucherName.setText(myVoucher.getNamegift());
        txtDiscountAmount.setText("Giảm: " + myVoucher.getDiscountAmount() + " đ");

        // Set trạng thái checkbox dựa trên selectedPosition
        checkBoxSelect.setChecked(position == selectedPosition);

        // Xử lý sự kiện chọn checkbox
        checkBoxSelect.setOnClickListener(v -> {
            selectedPosition = position; // Lưu vị trí được chọn
            notifyDataSetChanged(); // Cập nhật lại danh sách để reset trạng thái các checkbox
        });

        return convertView;
    }

    // Hàm trả về voucher được chọn
    public MyVoucher getSelectedVoucher() {
        if (selectedPosition != -1) {
            return myVoucherList.get(selectedPosition);
        }
        return null;
    }
}
