package com.example.duan_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan_android.Adapter.DealAdapter;
import com.example.duan_android.Fragment.AccountFragment;
import com.example.duan_android.Model.deal;
import com.example.duan_android.R;

import java.util.ArrayList;

public class DealActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<deal> arrayList;
    private DealAdapter adapter;
    private ImageView imgClose;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_deal);
        lv= (ListView) findViewById(R.id.lvdeal);
        arrayList=new ArrayList<>();
        arrayList.add(new deal(R.drawable.cam,"Cám","2D-SUB","T18","Rạp 2","Glaxy Nguyễn Du","11:00","Thứ 5, 26/09/2024"));
        arrayList.add(new deal(R.drawable.image_cmt1,"Venom","2D-SUB","T19","Rạp 1","Glaxy Nguyễn Du","12:00","Thứ 6, 27/09/2024"));
        arrayList.add(new deal(R.drawable.image_cmt3,"Joker","2D-SUB","T20","Rạp 3","Glaxy Nguyễn Du","16:00","Thứ 7, 28/09/2024"));
        adapter=new DealAdapter(this,R.layout.layout_deal,arrayList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getApplication(), QR.class);
                        break;
                    case 1:
                        intent = new Intent(getApplication(), QR1.class);
                        break;
                     case 2:
                        intent = new Intent(getApplication(), QR2.class);
                        break;
                    default:
                        return;
                }
                startActivity(intent);
            }
        });

        imgClose=findViewById(R.id.btn_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
