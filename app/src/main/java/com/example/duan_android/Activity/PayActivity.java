package com.example.duan_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan_android.Adapter.AdapterCinema;
import com.example.duan_android.Adapter.PayAdapter;
import com.example.duan_android.Model.Pay;
import com.example.duan_android.Model.cinema;
import com.example.duan_android.R;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {
    private ImageButton btn_back;
    private Button btn_voucher;
    private ListView lv;
    private ArrayList<Pay> payList;
    private PayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_back = findViewById(R.id.btn_back);
        lv = findViewById(R.id.lv_pay);
        payList = new ArrayList<>();
        payList.add(new Pay(R.drawable.ic_shopee, "Ví ShopeePay - Nhập mã SPPCINE10 giảm ngay 10k"));
        payList.add(new Pay(R.drawable.ic_momo, "Ví momo"));
        payList.add(new Pay(R.drawable.ic_zalopay, "ZaloPay - Bạn mới nhập GIAMCINE - giảm 50%"));
        adapter = new PayAdapter(this, R.layout.item_pay, payList);

        lv.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_voucher = findViewById(R.id.btn_khuyenmai);
        btn_voucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayActivity.this, AddDiscountActivity.class);
                startActivity(intent);
            }
        });
    }
}