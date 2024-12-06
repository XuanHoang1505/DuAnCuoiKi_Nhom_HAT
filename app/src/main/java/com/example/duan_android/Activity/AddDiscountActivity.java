package com.example.duan_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.AdapterMyVoucher; // Sử dụng AdapterMyVoucher thay vì AdapterKhachHangVoucher
import com.example.duan_android.Model.MyVoucher; // Sử dụng model MyVoucher
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddDiscountActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private Button btn_save;
    private ListView lv;

    private AdapterMyVoucher adapterMyVoucher; // Thay đổi AdapterKhachHangVoucher thành AdapterMyVoucher
    private ArrayList<MyVoucher> arrayList; // Sử dụng MyVoucher thay vì KhachHangVoucher

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discount);

        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        lv = findViewById(R.id.lv_voucher);
        arrayList = new ArrayList<>();
        adapterMyVoucher = new AdapterMyVoucher(this, R.layout.item_voucher, arrayList); // Sử dụng AdapterMyVoucher
        lv.setAdapter(adapterMyVoucher);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId != null) {
            // Gửi yêu cầu đến API để lấy danh sách voucher
            loadVoucherData(userId);
        }

        // Xử lý sự kiện click back button
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Xử lý sự kiện click save button
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyVoucher selectedVoucher = adapterMyVoucher.getSelectedVoucher();

                if (selectedVoucher != null) {
                    Log.d("AddDiscountActivity", "Selected Voucher ID: " + selectedVoucher.getId());
                    SharedPreferences sharedPreferences = getSharedPreferences("VoucherPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("SelectedVoucherId", selectedVoucher.getId());
                    editor.putString("soTienGiam", selectedVoucher.getDiscountAmount()) ;
                    editor.apply();
                    Intent intent = new Intent(AddDiscountActivity.this, PayActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Hiển thị thông báo nếu chưa chọn voucher
                    CheckConnection.ShowToast_Short(AddDiscountActivity.this, "Vui lòng chọn một voucher!");
                }
            }
        });


    }

    private void loadVoucherData(String userId) {
        String url = Server.path_MyVoucher + "?userId=" + userId;

        if (CheckConnection.haveNetworkConnection(this)) {
            Log.d("ConnectionStatus", "Network is connected.");

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Response", response.toString()); // Kiểm tra dữ liệu trả về từ API

                            if (response != null && response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject voucherJson = response.getJSONObject(i);
                                        int IDVoucher = voucherJson.getInt("IDVoucher");
                                        String hinhAnh = voucherJson.getString("HinhAnh");
                                        int hinh = getResources().getIdentifier(hinhAnh, "drawable", getPackageName());
                                        String tenVoucher = voucherJson.getString("TenVoucher");
                                        String discountAmount = voucherJson.getString("SoTienGiam");

                                        MyVoucher voucher = new MyVoucher(IDVoucher,hinh, tenVoucher, discountAmount);
                                        arrayList.add(voucher);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                adapterMyVoucher.notifyDataSetChanged();
                            } else {
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VolleyError", error.toString());
                        }
                    });

            requestQueue.add(jsonArrayRequest);
        } else {
            Log.d("ConnectionStatus", "No network connection.");
            CheckConnection.ShowToast_Short(this, "Bạn hãy kiểm tra lại kết nối");
        }
    }
}
