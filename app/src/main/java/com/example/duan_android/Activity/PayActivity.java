package com.example.duan_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.PayAdapter;
import com.example.duan_android.Model.Pay;
import com.example.duan_android.Model.cinema;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PayActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private Button btn_voucher,btnPay;
    private ListView lv;
    private PayAdapter payAdapter;
    private PayAdapter adapter;
    private TextView text_movie, text_movieAddress, text_room, text_time, text_date, giaCombo, giaVe, tenGhe, totalPrice_hoaDon1, tenCombo, totalPrice_hoaDon2;
    private ImageView img;
    private ArrayList<Pay> payList;
    private int soTienGiam = 0;
    private int IDCombo = 0;
    int soLuong=0;
    double tongTien=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        btn_back = findViewById(R.id.btn_back);
        text_movie = findViewById(R.id.text_movie);
        text_movieAddress = findViewById(R.id.text_movieAddress);
        text_room = findViewById(R.id.text_room);
        text_time = findViewById(R.id.text_time);
        text_date = findViewById(R.id.text_date);
        tenCombo = findViewById(R.id.tenCombo);
        giaCombo = findViewById(R.id.giaCombo);
        giaVe = findViewById(R.id.giaVe);
        tenGhe = findViewById(R.id.tenGhe);
        totalPrice_hoaDon1 = findViewById(R.id.totalPrice_hoaDon1);
        totalPrice_hoaDon2 = findViewById(R.id.totalPrice_hoaDon2);
        lv = findViewById(R.id.lv_pay);
        img = findViewById(R.id.image_movie);
        btnPay = findViewById(R.id.btnPay);
        payList = new ArrayList<>();
        payList.add(new Pay(R.drawable.ic_shopee, "Ví ShopeePay - Nhập mã SPPCINE10 giảm ngay 10k"));
        payList.add(new Pay(R.drawable.ic_momo, "Ví momo"));
        payList.add(new Pay(R.drawable.ic_zalopay, "ZaloPay - Bạn mới nhập GIAMCINE - giảm 50%"));
        // Initialize the PayAdapter
        adapter = new PayAdapter(this, R.layout.item_pay, payList);
        lv.setAdapter(adapter);

        // Set up the back button
        btn_back.setOnClickListener(view -> finish());

        // Set up the voucher button
        btn_voucher = findViewById(R.id.btn_khuyenmai);
        btn_voucher.setOnClickListener(view -> {
            Intent intent = new Intent(PayActivity.this, AddDiscountActivity.class);
            startActivity(intent);
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String idKh = sharedPreferences.getString("userId", null);

        SharedPreferences sharedPreferencesVe = getSharedPreferences("saveidve", MODE_PRIVATE);
        int idVe = sharedPreferencesVe.getInt("IDVe", -1);

        SharedPreferences sharedPreferencesLichChieu = getSharedPreferences("shareIDLichChieu", MODE_PRIVATE);
        int idLichChieu = sharedPreferencesLichChieu.getInt("idLichChieu", -1);

        if (idVe != -1 && idLichChieu != -1) {
            getThongTin(idVe, idLichChieu);
        } else {
            Toast.makeText(this, "Movie details not available", Toast.LENGTH_SHORT).show();
        }

        SharedPreferences sharedPreferencesb = getSharedPreferences("VoucherPrefs", MODE_PRIVATE);
        int selectedVoucherId = sharedPreferencesb.getInt("SelectedVoucherId", 0);

        SharedPreferences sharedTenCombo = getSharedPreferences("VoucherPrefs", MODE_PRIVATE);
        String  tenCombo = sharedTenCombo.getString("ComboNames", null);

        SharedPreferences sharedSoLuong = getSharedPreferences("VoucherPrefs", MODE_PRIVATE);
        String  soLuongstr = sharedSoLuong.getString("ComboQuantities", null);

        if (!soLuongstr.isEmpty()){
            String cleanedQuantity = soLuongstr.trim();
            soLuong = Integer.parseInt(cleanedQuantity);
        }

        getIDCombo(tenCombo, new IDComboCallback() {
            @Override
            public void onSuccess(int idCombo) {
                IDCombo = idCombo;
                Log.d("ComboActivity", "IDCombo: " + IDCombo);
                // Tiến hành các thao tác khác với IDCombo nếu cần
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("ComboActivity", "Error retrieving IDCombo", e);
            }
        });
        Log.d("PayActivity", "Id đã chọn: " + selectedVoucherId);

        if (selectedVoucherId != -1) {
            loadDiscountAmount(selectedVoucherId);
        }


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Log thông tin trước khi gọi createhd
                Log.d("PayActivity", "Thông tin thanh toán: ");
                Log.d("PayActivity", "ID Khách hàng: " + idKh);
                Log.d("PayActivity", "Selected Voucher ID: " + selectedVoucherId);
                Log.d("PayActivity", "ID Vé: " + idVe);
                Log.d("PayActivity", "ID Combo: " + IDCombo);
                Log.d("PayActivity", "Số lượng: " + soLuong);
                Log.d("PayActivity", "Tổng tiền: " + tongTien);

                // Gọi phương thức createhd
                createhd(idKh, selectedVoucherId, idVe, IDCombo, soLuong, tongTien);

                // Hiển thị thông báo
                Toast.makeText(PayActivity.this, "Đặt vé thành công!", Toast.LENGTH_SHORT).show();

                // Chuyển sang MainActivity
                Intent intent = new Intent(PayActivity.this, MainActivity.class);
                startActivity(intent);

                // Kết thúc activity hiện tại
                finish();
            }
        });


    }

    private void getThongTin(int idVe, int idLichChieu) {
        String url = Server.getThongTin + "idVe=" + idVe + "&idLichChieu=" + idLichChieu;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Log.d("PayActivity", "JSON Response: " + response.toString());

                        // Danh sách tên ghế
                        ArrayList<String> seatNames = new ArrayList<>();
                        double ticketPrice = 0;

                        // Lặp qua từng phần tử trong mảng JSON
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject movieDetail = response.getJSONObject(i);

                            // Lấy thông tin phim (chỉ cần từ phần tử đầu tiên)
                            if (i == 0) {
                                String movieName = movieDetail.getString("TenPhim");
                                String movieAddress = movieDetail.getString("TenRap");
                                String HinhAnh = movieDetail.getString("HinhAnh");
                                int Anh = getResources().getIdentifier(HinhAnh, "drawable", getPackageName());
                                String roomName = movieDetail.getString("TenPhongChieu");
                                String time = movieDetail.getString("GioBatDau");
                                String date = movieDetail.getString("NgayChieu");

                                text_movie.setText(movieName);
                                text_movieAddress.setText(movieAddress);
                                text_room.setText(roomName);
                                text_time.setText(time);
                                text_date.setText(date);
                                img.setImageResource(Anh);

                                SharedPreferences sharedPreferences = getSharedPreferences("VoucherPrefs", MODE_PRIVATE);
                                String comboDetails = sharedPreferences.getString("ComboDetails", "");
                                String totalPriceComboStr = sharedPreferences.getString("TotalPrice", "0");

                                // Gán dữ liệu vào TextView
                                if (comboDetails != null && !comboDetails.isEmpty()) {
                                    tenCombo.setText(comboDetails);
                                } else {
                                    tenCombo.setText("");
                                }

                                if (totalPriceComboStr != null && !totalPriceComboStr.isEmpty()) {
                                    giaCombo.setText(totalPriceComboStr + " VND");
                                } else {
                                    giaCombo.setText("");
                                }

                                double totalPriceCombo = 0;
                                if (totalPriceComboStr != null && !totalPriceComboStr.isEmpty()) {
                                    try {
                                        totalPriceCombo = Double.parseDouble(totalPriceComboStr);
                                    } catch (NumberFormatException e) {
                                        giaCombo.setText("0 VND");
                                        totalPriceCombo = 0;
                                    }
                                }

                                ticketPrice = movieDetail.getDouble("GiaVe");
                                double totalPrice = ticketPrice + totalPriceCombo - soTienGiam;
                                
                                totalPrice_hoaDon1.setText(totalPrice + " VND");
                                totalPrice_hoaDon2.setText(totalPrice + " VND");
                                String rawPrice = totalPrice_hoaDon2.getText().toString(); // Chuỗi gốc
                                Log.d("PRICE_LOG", "Raw Price: " + rawPrice); // Log chuỗi gốc

                                String sanitizedPrice = rawPrice.replace("d", "").replace("VND","");
                                Log.d("PRICE_LOG", "Sanitized Price: " + sanitizedPrice); // Log chuỗi đã làm sạch

                                tongTien = Double.parseDouble(sanitizedPrice); // Chuyển đổi sang kiểu double
                                Log.d("PRICE_LOG", "Total Price (double): " + tongTien); // Log giá trị tổng tiền

                            }

                            // Lấy tên ghế
                            String seatName = movieDetail.getString("TenGhe");
                            seatNames.add(seatName);
                        }

                        // Hiển thị thông tin ghế
                        String seats = String.join(", ", seatNames);
                        tenGhe.setText("x" + seatNames.size() + " ghế " + seats);

                        // Hiển thị giá vé
                        giaVe.setText(ticketPrice + " VND");
                    } catch (Exception e) {
                        Log.e("PayActivity", "Error parsing movie details", e);
                        Toast.makeText(PayActivity.this, "Error parsing movie details", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("PayActivity", "Error fetching movie details", error);
                    Toast.makeText(PayActivity.this, "Error fetching movie details", Toast.LENGTH_SHORT).show();
                });

        // Thêm request vào hàng đợi
        requestQueue.add(jsonArrayRequest);
    }

    private void loadDiscountAmount(int idVoucher) {
        String url = Server.getSoTienGiam + "idVoucher=" + idVoucher;

        if (CheckConnection.haveNetworkConnection(this)) {
            Log.d("ConnectionStatus", "Network is connected.");

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        Log.d("Response", "Full Response: " + response.toString()); // Log toàn bộ phản hồi

                        if (response != null) {
                            // Kiểm tra và lấy giá trị giảm giá từ JSON
                            String discountAmountStr = response.optString("SoTienGiam", "0");
                            Log.d("DiscountAmount", "Discount amount from response: " + discountAmountStr); // Log giá trị giảm giá

                            if (!discountAmountStr.isEmpty() && !discountAmountStr.equals("0")) {
                                try {
                                    soTienGiam = (int) Double.parseDouble(discountAmountStr);

                                    updateUIAfterDiscount();
                                } catch (NumberFormatException e) {
                                    Log.e("DiscountAmount", "Error parsing discount amount: " + e.getMessage());
                                }
                            } else {
                                Log.d("DiscountAmount", "No valid discount found.");
                            }
                        } else {
                            Log.d("Response", "No response data found.");
                        }
                    },
                    error -> {
                        // Log lỗi và phản hồi từ server
                        Log.e("PayActivity", "Error loading discount amount", error);
                        if (error.networkResponse != null) {
                            Log.d("ErrorCode", "Error Code: " + error.networkResponse.statusCode);
                        }
                        Toast.makeText(PayActivity.this, "Error loading discount amount", Toast.LENGTH_SHORT).show();
                    });

            requestQueue.add(jsonObjectRequest);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }
    private void createhd(String idkh, int idvc, int idve, int idcb, int slcb, double tongtien) {
        String url = Server.posthd;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(PayActivity.this, response, Toast.LENGTH_SHORT).show();
                    if (response.contains("Mua Thanh Cong")) {
                        Intent intent = new Intent(PayActivity.this, DealActivity.class);
                        startActivity(intent);
                    }
                },
                error -> {
                    // Xử lý lỗi
                    Log.e("VolleyError", error.toString());
                    Toast.makeText(PayActivity.this, ",,,,,,,,,,,", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Gửi dữ liệu POST đến server
                Map<String, String> params = new HashMap<>();
                params.put("IDKhachHang", idkh);
                params.put("IDVoucher", String.valueOf(idvc));
                params.put("IDVe", String.valueOf(idve));
                params.put("IDCombo", String.valueOf(idcb));
                params.put("SoLuongCombo", String.valueOf(slcb));
                params.put("TongTien", String.valueOf(tongtien));
                Log.d("Params", "IDKhachHang: " + idkh + ", IDVoucher: " + idvc + ", IDVe: " + idve + ", IDCombo: " + idcb + ", SoLuongCombo: " + slcb + ", TongTien: " + tongtien);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
        // Thêm request vào hàng đợi

    private void getIDCombo(String tenCombo, final IDComboCallback callback) {
        String url = Server.getIdComboByname + tenCombo;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Kiểm tra xem JSON có rỗng không
                    if (response != null && response.has("IDCombo")) {
                        int IDCombo = response.getInt("IDCombo");
                        callback.onSuccess(IDCombo);  // Gọi callback với IDCombo
                    } else {
                        // Nếu JSON rỗng hoặc không có IDCombo, gán IDCombo = -1
                        callback.onSuccess(-1);
                    }
                } catch (Exception e) {
                    Log.e("ComboActivity", "Error parsing combo details", e);
                    callback.onFailure(e);  // Nếu có lỗi khi phân tích dữ liệu
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ComboActivity", "Error fetching combo details", error);
                callback.onFailure(error);  // Nếu có lỗi khi gửi yêu cầu
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    // Định nghĩa interface callback để lấy kết quả
    public interface IDComboCallback {
        void onSuccess(int IDCombo);
        void onFailure(Exception e);
    }

    private void updateUIAfterDiscount() {
        // Update the total price after applying the discount
        double originalPrice = Double.parseDouble(giaVe.getText().toString().replace(" VND", "").trim());
        double finalPrice = originalPrice + Double.parseDouble(giaCombo.getText().toString().replace(" VND", "").trim()) - soTienGiam;

        totalPrice_hoaDon1.setText(finalPrice + " VND");
        totalPrice_hoaDon2.setText(finalPrice + " VND");
        String rawPrice = totalPrice_hoaDon2.getText().toString(); // Chuỗi gốc
        String sanitizedPrice = rawPrice.replaceAll("[^\\d]", "");
        tongTien = Double.parseDouble(sanitizedPrice);
        // Show a toast confirming the discount was applied
        Toast.makeText(PayActivity.this, "Đã giảm: " + soTienGiam + " VND", Toast.LENGTH_SHORT).show();
    }
}
