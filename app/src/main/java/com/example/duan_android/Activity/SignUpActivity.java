package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private TextView tv_login;
    private EditText name,pass,sdt,ngaysinh,email,mk;
    private Button btntdangky;
    private CheckBox nam,nu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv_login = findViewById(R.id.tv_login);
        name=findViewById(R.id.et_name);
        pass=findViewById(R.id.et_password);
        ngaysinh=findViewById(R.id.et_dob);
        email=findViewById(R.id.et_email);
        sdt=findViewById(R.id.et_phonenumber);
        nam=findViewById(R.id.checkbox_nam);
        nu=findViewById(R.id.checkbox_nu);
        mk=findViewById(R.id.et_cfpassword);
        btntdangky=findViewById(R.id.btn_signUp);
        btntdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = name.getText().toString().trim();
                String matkhau = pass.getText().toString().trim();
                String ngaySinh = ngaysinh.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String soDienThoai = sdt.getText().toString().trim();
                String gioiTinh = nam.isChecked() ? "M" : (nu.isChecked() ? "F" : "");
                String ktmk=mk.getText().toString().trim();

                // Kiểm tra dữ liệu đầu vào
                if (TextUtils.isEmpty(ten) || TextUtils.isEmpty(matkhau) || TextUtils.isEmpty(ngaySinh)
                        || TextUtils.isEmpty(mail) || TextUtils.isEmpty(soDienThoai) || TextUtils.isEmpty(gioiTinh) || TextUtils.isEmpty(ktmk) ) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!matkhau.equals(ktmk)) {
                    Toast.makeText(SignUpActivity.this, "Mật khẩu xác nhận không khớp mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Gửi dữ liệu đến server
                singup(ten, mail, soDienThoai, matkhau, ngaySinh, gioiTinh);
            };

        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nam.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nu.setChecked(false);
            }
        });

        nu.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                nam.setChecked(false);
            }
        });
    }

    private void singup(String ten, String mail, String soDienThoai, String matkhau, String ngaySinh, String gioiTinh)
    {
        String url= Server.dangky;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // Xử lý khi đăng ký thành công hoặc thất bại
                    Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                    if (response.contains("Đăng ký thành công")) {
                        finish(); // Quay lại màn hình trước
                    }
                },
                error -> {
                    Log.e("VolleyError", error.toString());
                    Toast.makeText(SignUpActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Gửi dữ liệu POST đến server
                Map<String, String> params = new HashMap<>();
                params.put("TenKhachHang", ten);
                params.put("Email", mail);
                params.put("DienThoai", soDienThoai);
                params.put("MatKhau", matkhau);
                params.put("NgaySinh", ngaySinh);
                params.put("GioiTinh", gioiTinh);
                return params;
            }
        };

        // Thêm request vào hàng đợi
        requestQueue.add(stringRequest);
    }

}