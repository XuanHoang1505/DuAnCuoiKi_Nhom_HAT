package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.AdapterCombo;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.Model.combo;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComboActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<combo> arrayList;
    private AdapterCombo adapter;
    private Button btnNext;
    private TextView ttcombo, totalPrice, comboDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_combo);

        lv = findViewById(R.id.lvcombo);
        btnNext = findViewById(R.id.btnNext);
        ttcombo = findViewById(R.id.ttcombo);
        totalPrice = findViewById(R.id.totalPrice);
        comboDetails = findViewById(R.id.comboDetails);  // TextView mới để hiển thị thông tin combo đã chọn

        arrayList = new ArrayList<>();
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

        if (CheckConnection.haveNetworkConnection(getApplication())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.combo, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    if (response != null) {
                        int ID, hinhanh;
                        String tenhinh, tencb, mota;
                        double gia;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ID = jsonObject.getInt("IDCB");
                                tencb = jsonObject.getString("TenCB");
                                mota = jsonObject.getString("MoTa");
                                tenhinh = jsonObject.getString("HinhAnh");
                                hinhanh = getResources().getIdentifier(tenhinh, "drawable", getPackageName());
                                gia = jsonObject.getDouble("Gia");

                                arrayList.add(new combo(ID, tencb, mota, hinhanh, gia));
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

            requestQueue.add(jsonArrayRequest);
        } else {
            CheckConnection.ShowToast_Short(getApplication(), "Bạn hãy kiểm tra lại kết nối");
        }

        adapter = new AdapterCombo(this, R.layout.layout_combo, arrayList);
        lv.setAdapter(adapter);
    }

    public void updateComboDetails(String comboName, int quantity) {
        if (quantity == 0) {
            comboDetails.setText("");
        } else {
            comboDetails.setText("Combo: " + comboName + " - Số lượng: " + quantity);
        }
    }

    // Hàm cập nhật tổng giá trị
    public void updateTotalPrice(double total) {
        totalPrice.setText(""+total);
    }

}
