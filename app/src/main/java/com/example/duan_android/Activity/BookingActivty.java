package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.SeatAdapter;
import com.example.duan_android.Model.Seat;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingActivty extends AppCompatActivity {

    private TextView totalPriceTextView;
    private RecyclerView recyclerView;
    private SeatAdapter seatAdapter;
    private List<Seat> seatList;
    private int totalPrice = 0;
    private int countSeat = 0; // Biến lưu số lượng ghế đã chọn
    private ImageView btn_back;
    private TextView txtcountSeat, txtseatName, txt_movieName, movie_name;
    private Button continueButton, giochieu;
    private List<String> selectedSeats = new ArrayList<>();  // Biến để lưu tên các ghế đã chọn
    private List<Integer> selectedSeatIds = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_activty);

        totalPriceTextView = findViewById(R.id.totalPrice);
        recyclerView = findViewById(R.id.recyclerViewSeats);
        btn_back = findViewById(R.id.btn_back);
        giochieu = findViewById(R.id.giochieu);
        continueButton = findViewById(R.id.continueButton);
        txtcountSeat = findViewById(R.id.countSeat);
        txtseatName = findViewById(R.id.seatName);
        txt_movieName = findViewById(R.id.txt_movieName);
        movie_name = findViewById(R.id.movie_name);
        txtcountSeat.setText("x" + countSeat);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chỉ chuyển sang ComboActivity nếu có ghế được chọn
                if (countSeat > 0) {
                    int idLichChieu = getIntent().getIntExtra("idLichChieu", -1);
                    String selectedSeatsJson = new JSONArray(selectedSeatIds).toString();  // Chuyển danh sách ghế thành chuỗi JSON
                    createve(idLichChieu, totalPrice, selectedSeatsJson);

                    Intent intent = new Intent(BookingActivty.this, ComboActivity.class);
                    SharedPreferences sharedPreferencesLichChieu = getSharedPreferences("shareIDLichChieu", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferencesLichChieu.edit();
                    editor.putInt("idLichChieu", idLichChieu);
                    editor.apply();
                    startActivity(intent);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Vui lòng chọn ít nhất một ghế!");
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("ShareIdPhim", MODE_PRIVATE);
        String TenPhim = sharedPreferences.getString("TenPhim", null);
        txt_movieName.setText(TenPhim);
        movie_name.setText(TenPhim);
        seatList = new ArrayList<>();
        int idRap=getIntent().getIntExtra("idRap",-1);
        String tenRap = getIntent().getStringExtra("tenRap");
        String gioChieu = getIntent().getStringExtra("gioChieu");
        int idLichChieu = getIntent().getIntExtra("idLichChieu", -1); // Mặc định là -1 nếu không có ID
        giochieu.setText(gioChieu);

        // Kiểm tra kết nối mạng trước khi gọi API
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            loadSeatsFromAPI(idRap);
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }

        // Set up RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 9));
        seatAdapter = new SeatAdapter(this, seatList, new SeatAdapter.OnSeatSelectedListener() {
            @Override
            public void onSeatSelected(int priceChange, boolean isSelected, int seatId, String seatName) {
                totalPrice += priceChange;
                if (isSelected) {
                    countSeat++;
                    selectedSeats.add(seatName);
                    selectedSeatIds.add(seatId);
                } else {
                    countSeat--;
                    selectedSeats.remove(seatName);
                    selectedSeatIds.remove((Integer) seatId);
                }

                totalPriceTextView.setText(totalPrice + "đ");
                txtcountSeat.setText("x" + countSeat);

                String selectedSeatsText = String.join(", ", selectedSeats);
                txtseatName.setText(selectedSeatsText);

                // Log thông tin ghế đã chọn
                Log.d("SelectedSeats", "Số lượng ghế đã chọn: " + countSeat);
                Log.d("SelectedSeats", "Danh sách ghế đã chọn: " + selectedSeatsText);
                Log.d("SelectedSeatIds", "Danh sách ID ghế đã chọn: " + selectedSeatIds);
            }
        });
        recyclerView.setAdapter(seatAdapter);
    }

    private void loadSeatsFromAPI(int idRap) {
        String url = Server.getGhe + "?IDRap=" + idRap;

        // Create the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Create the GET request using JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Response", response.toString());
                        seatList.clear();

                        try {
                            // Loop through the list of seats in the JSON array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject seatObject = response.getJSONObject(i);
                                int idghe=seatObject.getInt("idghe");
                                String name = seatObject.getString("tenghe");
                                boolean isAvailable = seatObject.getInt("trangthai") == 1; // If there's no idve, it's available

                                // Add the seat to the list
                                seatList.add(new Seat(idghe,name, isAvailable));
                            }
                            // Update the adapter
                            seatAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Error loading seat data");
                    }
                });

        // Add the request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void createve(int idLichChieu, int giaVe, String selectedSeatsJson) {
        String url = Server.postve;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.getString("status");
                        String message = jsonResponse.getString("message");

                        Toast.makeText(BookingActivty.this, message, Toast.LENGTH_SHORT).show();
                        if (status.equals("success")) {
                            int idVe = jsonResponse.getInt("IDVe"); // Lấy mã vé từ server
                            Log.d("APIResponse", "IDVe: " + idVe);
                            Log.d("SelectedSeats", "Danh sách ghế đã chọn: " + selectedSeatsJson);
                            SharedPreferences sharedPreferences=getSharedPreferences("saveidve",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("IDVe", idVe);
                            editor.apply();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Log.e("VolleyError", error.toString());
                    Toast.makeText(BookingActivty.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Gửi dữ liệu POST đến server dưới dạng Map
                Map<String, String> params = new HashMap<>();
                params.put("IDLichChieu", String.valueOf(idLichChieu));
                params.put("GiaVe", String.valueOf(giaVe));
                params.put("GheDaChon", selectedSeatsJson); // Gửi danh sách ghế dưới dạng chuỗi JSON
                return params;
            }
        };

        // Thêm request vào hàng đợi
        requestQueue.add(stringRequest);
    }
}