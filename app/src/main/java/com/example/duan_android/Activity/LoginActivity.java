package com.example.duan_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button btn_login;
    private TextView txtview_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtview_signup = findViewById(R.id.txtview_signup);
        btn_login = findViewById(R.id.btn_login);
        emailEditText=findViewById(R.id.edittxt_username);
        passwordEditText=findViewById(R.id.edittxt_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    login(email, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtview_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private void login(String email, String password) {
        if (CheckConnection.haveNetworkConnection(getApplication().getApplicationContext())) {
            Log.d("ConnectionStatus", "Network is connected.");

            // URL của API đăng nhập
            String url = Server.login;

            // Tạo yêu cầu GET với Volley (vì server trả về mảng JSON)
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                Log.d("LoginResponse", "Response: " + response.toString());

                                boolean isLoginSuccessful = false;

                                // Duyệt qua mảng JSON
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject userJson = response.getJSONObject(i);
                                    String serverEmail = userJson.getString("email");
                                    String serverPassword = userJson.getString("mk");

                                    // Kiểm tra thông tin đăng nhập
                                    if (serverEmail.equals(email) && serverPassword.equals(password)) {
                                        // Đăng nhập thành công
                                        String userId = userJson.getString("id");
                                        String userName = userJson.getString("ten");
                                        String Email = userJson.getString("email");
                                        String ngaysinh = userJson.getString("ngaysinh");
                                        String gioitinh = userJson.getString("gioitinh");
                                        String dienthoai = userJson.getString("dienthoai");
                                        String userDiemThuong = userJson.getString("diem");

                                        saveUserInfo(userId, userName,Email,ngaysinh,gioitinh,dienthoai, userDiemThuong);

                                        // Chuyển sang màn hình chính hoặc trang cá nhân
                                        Intent intent = new Intent(getApplication(), MainActivity.class);
                                        startActivity(intent);
                                        finish();  // Đóng màn hình đăng nhập
                                        isLoginSuccessful = true;
                                        break;
                                    }
                                }

                                if (!isLoginSuccessful) {
                                    // Nếu không tìm thấy người dùng hoặc thông tin đăng nhập sai
                                    Log.d("Login", "Login failed: Invalid credentials.");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("LoginResponse", "JSONException in response: " + e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VolleyError", error.toString());
                            error.printStackTrace();
                        }
                    });

            // Thêm yêu cầu vào hàng đợi
            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
            requestQueue.add(jsonArrayRequest);

        } else {
            Log.d("ConnectionStatus", "No network connection.");
            CheckConnection.ShowToast_Short(getApplication(), "Bạn hãy kiểm tra lại kết nối");
        }
    }





    private void saveUserInfo(String id, String name,String useremail,String ngaysinh,String gioitinh,String dienthoai, String diemThuong) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", id);
        editor.putString("userName", name);
        editor.putString("diemThuong", diemThuong);
        editor.putString("useremail", useremail);
        editor.putString("ngaysinh", ngaysinh);
        editor.putString("gioitinh", gioitinh);
        editor.putString("dienthoai", dienthoai);
        editor.apply();
    }
}