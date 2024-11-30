package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan_android.R;

public class InformationActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView name_title;
    private EditText et_name;
    private EditText et_phoneNumber;
    private EditText et_Dob;
    private EditText et_email;
    private CheckBox checkbox_nam;
    private CheckBox checkbox_nu;

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

        // Đặt sự kiện cho nút quay lại
        imgBack.setOnClickListener(view -> finish());

        // Lấy dữ liệu từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", "");
        String dienthoai = sharedPreferences.getString("dienthoai", "");
        String useremail = sharedPreferences.getString("useremail", "");
        String ngaysinh = sharedPreferences.getString("ngaysinh", "");
        String gioitinh = sharedPreferences.getString("gioitinh", "");

        // Điền dữ liệu vào các EditText và CheckBox
        et_name.setText(userName);
        et_email.setText(useremail);
        et_Dob.setText(ngaysinh);
        et_phoneNumber.setText(dienthoai);
        name_title.setText(userName);

        // Kiểm tra giới tính và cập nhật trạng thái cho CheckBox
        if (gioitinh != null && gioitinh.equals("M")) {
            checkbox_nam.setChecked(true);
            checkbox_nu.setChecked(false);
        } else if (gioitinh != null && gioitinh.equals("F")) {
            checkbox_nam.setChecked(false);
            checkbox_nu.setChecked(true);
        }

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
    }
}
