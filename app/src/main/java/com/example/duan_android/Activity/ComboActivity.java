package com.example.duan_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan_android.Adapter.AdapterCombo;
import com.example.duan_android.Model.combo;
import com.example.duan_android.R;

import java.util.ArrayList;

public class ComboActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<combo> arrayList;
    private AdapterCombo adapter;
    private Button btnNext;
    private TextView ttcombo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_combo);
        lv=(ListView) findViewById(R.id.lvcombo);
        btnNext = findViewById(R.id.btnNext);
        ttcombo = findViewById(R.id.ttcombo);

        ttcombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComboActivity.this, PayActivity.class);
                startActivity(intent);
            }
        });
        arrayList=new ArrayList<>();
        arrayList.add(new combo(R.drawable.combo1,"iCombo 1 Big Extra STD","1 Ly nước ngọt size L + 01 Hộp bắp + 1 Snack",109000  ));
        arrayList.add(new combo(R.drawable.combo2,"iCombo 1 Extra STD","1 Ly nước ngọt size L + 01 Hộp bắp",85000));
        arrayList.add(new combo(R.drawable.combo3,"Drink","1 Ly nước ngọt size L",40000));
        adapter=new AdapterCombo(this,R.layout.layout_combo,arrayList);
        lv.setAdapter(adapter);
    }
}
