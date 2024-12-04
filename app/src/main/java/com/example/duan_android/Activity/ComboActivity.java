package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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
import java.util.HashMap;
import java.util.Map;

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
                SharedPreferences sharedPreferences=getSharedPreferences("saveidve",MODE_PRIVATE);
                int idVe=sharedPreferences.getInt("IDVe",-1);
                deleteve(idVe);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xóa thông tin idVoucher khỏi SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("VoucherPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("SelectedVoucherId"); // Xóa idVoucher

                // Chuỗi tổng hợp chỉ chứa tên combo
                StringBuilder comboNamesBuilder = new StringBuilder();
                // Chuỗi tổng hợp tên combo và số lượng
                StringBuilder comboDetailsBuilder = new StringBuilder();
                // Chuỗi lưu số lượng combo
                StringBuilder comboQuantitiesBuilder = new StringBuilder();

                for (combo cb : arrayList) {
                    if (cb.getSoluong() > 0) {
                        // Lưu tên combo vào comboNamesBuilder
                        comboNamesBuilder.append(cb.getTitle())
                                .append("\n");

                        // Lưu tên combo và số lượng vào comboDetailsBuilder
                        comboDetailsBuilder.append(cb.getTitle())
                                .append(" x")
                                .append(cb.getSoluong())
                                .append("\n");

                        // Lưu số lượng combo vào comboQuantitiesBuilder
                        comboQuantitiesBuilder.append(cb.getSoluong())
                                .append("\n");
                    }
                }

                // In log các chuỗi trước khi lưu vào SharedPreferences
                Log.d("ComboActivity", "Combo Names: " + comboNamesBuilder.toString());
                Log.d("ComboActivity", "Combo Details: " + comboDetailsBuilder.toString());
                Log.d("ComboActivity", "Combo Quantities: " + comboQuantitiesBuilder.toString());
                Log.d("ComboActivity", "Total Price: " + totalPrice.getText().toString());

                // Lưu cả ba chuỗi vào SharedPreferences
                editor.putString("ComboNames", comboNamesBuilder.toString());
                editor.putString("ComboDetails", comboDetailsBuilder.toString());
                editor.putString("ComboQuantities", comboQuantitiesBuilder.toString()); // Lưu số lượng combo
                editor.putString("TotalPrice", totalPrice.getText().toString());
                editor.apply();

                // Chuyển sang PayActivity
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
    private void deleteve(int idVe){
        String url=Server.deleteve;
        RequestQueue requestQueue=Volley.newRequestQueue(getApplication());
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {

                },
                error -> Toast.makeText(getApplication(), "Lỗi kết nối", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("IDVe", String.valueOf(idVe));
                return params;
            }
        };
        requestQueue.add(request);
    }
}
