package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

public class InformationActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView name_title;
    private EditText et_name;
    private EditText et_phoneNumber;
    private EditText et_Dob;
    private EditText et_email;
    private CheckBox checkbox_nam;
    private CheckBox checkbox_nu;
    private Button btn_update;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // Sử dụng WindowInsets để xử lý việc vẽ cạnh màn hình (edge-to-edge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các View
        name_title =findViewById(R.id.name_title);
        imgBack = findViewById(R.id.img_back);
        et_name = findViewById(R.id.et_name);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        et_Dob = findViewById(R.id.et_Dob);
        et_email = findViewById(R.id.et_email);
        checkbox_nam = findViewById(R.id.checkbox_nam);
        checkbox_nu = findViewById(R.id.checkbox_nu);
        btn_update = findViewById(R.id.btn_update);

        // Đặt sự kiện cho nút quay lại
        imgBack.setOnClickListener(view -> finish());

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String idUser = sharedPreferences.getString("userId","");

        int id = Integer.parseInt(idUser);
        Log.d("Infor","id kh"+id);
        fetchUserDetails(id);
        checkbox_nam.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkbox_nu.setChecked(false);
            }
        });

        checkbox_nu.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkbox_nam.setChecked(false);
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                String idUser = sharedPreferences.getString("userId","");
                int id = Integer.parseInt(idUser);

                String email = et_email.getText().toString();
                String tenKhachHang = et_name.getText().toString();
                String dienThoai = et_phoneNumber.getText().toString();
                String ngaySinh = et_Dob.getText().toString();
                String gioiTinh;
                if(checkbox_nam.isChecked()){
                    gioiTinh = "M";
                }
                else
                    gioiTinh = "F";
                updateUserDetails(id,tenKhachHang, email, dienThoai, ngaySinh, gioiTinh);
            }
        });
    }
    private void fetchUserDetails(int idUser) {
        String url = Server.path_getUserById + idUser;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Lấy dữ liệu từ JSON Object
                    String tenKhachHang = response.getString("TenKhachHang");
                    String email = response.getString("Email");
                    String dienThoai = response.getString("DienThoai");
                    String ngaySinh = response.getString("NgaySinh");
                    String gioiTinh = response.getString("GioiTinh");
                    int diemThuong = response.getInt("DiemThuong");

                    // Cập nhật giao diện
                    name_title.setText(tenKhachHang);
                    et_name.setText(tenKhachHang);
                    et_email.setText(email);
                    et_phoneNumber.setText(dienThoai);
                    et_Dob.setText(ngaySinh);
                    if (gioiTinh != null && gioiTinh.equals("M")) {
                        checkbox_nam.setChecked(true);
                        checkbox_nu.setChecked(false);
                    } else if (gioiTinh != null && gioiTinh.equals("F")) {
                        checkbox_nam.setChecked(false);
                        checkbox_nu.setChecked(true);
                    }

                } catch (Exception e) {
                    Log.e("FetchUserDetails", "Error parsing user details", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("FetchUserDetails", "Error fetching user details", error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    private void updateUserDetails(int idUser, String tenKhachHang, String email, String dienThoai, String ngaySinh, String gioiTinh) {
        String url = Server.path_updateUser;

        // Tạo JSONObject để gửi dữ liệu
        JSONObject postData = new JSONObject();
        try {
            postData.put("idUser", idUser);
            postData.put("TenKhachHang", tenKhachHang);
            postData.put("Email", email);
            postData.put("DienThoai", dienThoai);
            postData.put("NgaySinh", ngaySinh);
            postData.put("GioiTinh", gioiTinh);
        } catch (JSONException e) {
            Log.e("UpdateUserDetails", "Error creating JSON object", e);
        }

        // Gửi yêu cầu POST với Volley
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Xử lý phản hồi từ server
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                String message = response.getString("message");
                                Toast.makeText(getApplicationContext(), "Cập nhật thất bại: " + message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("UpdateUserDetails", "Error parsing response", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("UpdateUserDetails", "Error sending update request", error);
                        Toast.makeText(getApplicationContext(), "Lỗi cập nhật thông tin!", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
