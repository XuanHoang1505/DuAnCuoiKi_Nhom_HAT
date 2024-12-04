package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.Model.deal;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;

public class QR extends AppCompatActivity {
    private Button btnclose;
    private ImageView img;
    private TextView name, room, cinema, time, date, ghengoi, tencombo, mave,diem, sum;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_qr);

        // Khởi tạo các view
        img = findViewById(R.id.img);
        name = findViewById(R.id.namemovie);
        cinema = findViewById(R.id.cinema);
        room = findViewById(R.id.room);
        time = findViewById(R.id.giochieu);
        date = findViewById(R.id.ngaychieu);
        ghengoi = findViewById(R.id.seat);
        tencombo = findViewById(R.id.combo);
        mave = findViewById(R.id.ticketcode);
        diem=findViewById(R.id.ticketstar);
        sum = findViewById(R.id.total);
        btnclose = findViewById(R.id.btn_close);

        // Nhận IDHoaDon từ Intent
        int idHoaDon = getIntent().getIntExtra("IDHoaDon", -1);
        Log.d("hd", "idhoadon."+idHoaDon);
        // Nếu IDHoaDon hợp lệ, gọi hàm lấy chi tiết
        if (idHoaDon != -1) {
            ChiTiet(idHoaDon);
        }

        // Đóng activity khi nhấn nút close
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ChiTiet(int idHoaDon) {
        String url = Server.detail + idHoaDon; // URL API lấy chi tiết giao dịch

        // Kiểm tra kết nối mạng
        if (CheckConnection.haveNetworkConnection(getApplication())) {

            RequestQueue requestQueue = Volley.newRequestQueue(getApplication());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        if (response.length() > 0) {
                            // Lấy dữ liệu từ JSON
                            org.json.JSONObject jsonObject = response.getJSONObject(0);
                            String tenhinh = jsonObject.getString("HinhAnh");
                            int hinh = getResources().getIdentifier(tenhinh, "drawable", getPackageName());
                            String tenphim = jsonObject.getString("TenPhim");
                            String tenrap = jsonObject.getString("TenRap");
                            String tenpc = jsonObject.getString("TenPhongChieu");
                            String giobd = jsonObject.getString("GioBatDau");
                            String ngaychieu = jsonObject.getString("NgayChieu");
                            String ghe = jsonObject.getString("GheNgoi");
                            String combo = jsonObject.getString("TenCombo");
                            int idve = jsonObject.getInt("IDVe");
                            int diemthuong=jsonObject.getInt("DiemThuong");
                            double tongtien = jsonObject.getDouble("TongTienVe");

                            // Tạo đối tượng deal
                            deal chiTietDeal = new deal(hinh, tenphim, tenrap, tenpc, giobd, ngaychieu, ghe, combo, idve,diemthuong, tongtien);

                            // Hiển thị dữ liệu lên giao diện
                            img.setImageResource(chiTietDeal.getHinhanh());
                            name.setText(chiTietDeal.getTenphim());
                            cinema.setText(chiTietDeal.getRapphim());
                            room.setText(chiTietDeal.getPhongchieu());
                            time.setText(chiTietDeal.getGiobatdau());
                            date.setText(chiTietDeal.getNgaychieu());
                            ghengoi.setText(chiTietDeal.getGhe());
                            mave.setText(String.valueOf(chiTietDeal.getIdve()));
                            tencombo.setText(chiTietDeal.getTencombo());
                            diem.setText(String.valueOf(chiTietDeal.getDiemthuong()));
                            sum.setText(String.valueOf(chiTietDeal.getTongtien()));
                        } else {
                            CheckConnection.ShowToast_Short(getApplication(), "Không tìm thấy dữ liệu");
                        }
                    } catch (Exception e) {
                        CheckConnection.ShowToast_Short(getApplication(), "Lỗi xử lý dữ liệu");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    CheckConnection.ShowToast_Short(getApplication(), "Lỗi khi tải dữ liệu");
                }
            });

            requestQueue.add(jsonArrayRequest);
        } else {
            CheckConnection.ShowToast_Short(getApplication(), "Bạn hãy kiểm tra lại kết nối");
        }
    }
}