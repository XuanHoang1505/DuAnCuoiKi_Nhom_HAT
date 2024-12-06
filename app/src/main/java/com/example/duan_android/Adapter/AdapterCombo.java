package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_android.Activity.ComboActivity;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        combo cb = arraylist.get(i);
        ImageView img = view.findViewById(R.id.combo_image);
        TextView title = view.findViewById(R.id.combo_title);
        TextView info = view.findViewById(R.id.combo_information);
        TextView price = view.findViewById(R.id.combo_price);
        TextView txtsl = view.findViewById(R.id.quantity);
        Button btnin = view.findViewById(R.id.increase);
        Button btnde = view.findViewById(R.id.decrease);

        txtsl.setText(String.valueOf(cb.getSoluong()));

        // Nút tăng số lượng
        // Nút tăng số lượng
        btnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = cb.getSoluong();
                cb.setSoluong(sl + 1);
                txtsl.setText(String.valueOf(cb.getSoluong()));
                updateTotalPrice(); // Cập nhật lại tổng giá trị

                // Cập nhật thông tin combo vào ComboActivity
                ((ComboActivity) context).updateComboDetails(cb.getTitle(), cb.getSoluong());
            }
        });

// Nút giảm số lượng
        btnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = cb.getSoluong();
                if (sl > 0) {
                    cb.setSoluong(sl - 1);
                }
                txtsl.setText(String.valueOf(cb.getSoluong()));
                updateTotalPrice(); // Cập nhật lại tổng giá trị

                // Cập nhật thông tin combo vào ComboActivity
                ((ComboActivity) context).updateComboDetails(cb.getTitle(), cb.getSoluong());
            }
        });


        // Cập nhật thông tin combo vào view
        img.setImageResource(cb.getImage());
        title.setText(cb.getTitle());
        info.setText(cb.getInfo());
        price.setText(String.valueOf(cb.getPrice()));

        return view;
    }

    // Hàm tính tổng giá trị cho tất cả combo
    private void updateTotalPrice() {
        double total = 0;
        for (combo cb : arraylist) {
            total += cb.getSoluong() * cb.getPrice();
        }
        // Cập nhật giá trị tổng vào Activity
        ((ComboActivity) context).updateTotalPrice(total);
    }
}
