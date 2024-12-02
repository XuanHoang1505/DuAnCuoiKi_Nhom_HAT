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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import java.util.List;

public class BookingActivty extends AppCompatActivity {

    private TextView totalPriceTextView;
    private RecyclerView recyclerView;
    private SeatAdapter seatAdapter;
    private List<Seat> seatList;
    private int totalPrice = 0;
    private int countSeat = 0; // Biến lưu số lượng ghế đã chọn
    private ImageView btn_back;
    private TextView txtcountSeat,txtseatName,txt_movieName,movie_name;
    private Button continueButton,giochieu;
    private List<String> selectedSeats = new ArrayList<>();  // Biến để lưu tên các ghế đã chọn

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_activty);

        totalPriceTextView = findViewById(R.id.totalPrice);
        recyclerView = findViewById(R.id.recyclerViewSeats);
        btn_back = findViewById(R.id.btn_back);
        giochieu =findViewById(R.id.giochieu);
        continueButton = findViewById(R.id.continueButton);
        txtcountSeat = findViewById(R.id.countSeat);
        txtseatName = findViewById(R.id.seatName);
        txt_movieName =findViewById(R.id.txt_movieName);
        movie_name =findViewById(R.id.movie_name);
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
                    Intent intent = new Intent(BookingActivty.this, ComboActivity.class);

                    // Truyền danh sách ghế đã chọn dưới dạng ArrayList<String>
                    intent.putStringArrayListExtra("selectedSeats", new ArrayList<>(selectedSeats));
                    // Truyền tổng giá trị
                    intent.putExtra("totalPrice", totalPrice);

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
        String tenRap = getIntent().getStringExtra("tenRap");
        String gioChieu = getIntent().getStringExtra("gioChieu");
        int idLichChieu = getIntent().getIntExtra("idLichChieu", -1); // Mặc định là -1 nếu không có ID
        giochieu.setText(gioChieu);
        // Kiểm tra kết nối mạng trước khi gọi API
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            loadSeatsFromAPI(tenRap);
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }

        // Set up RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 9));
        seatAdapter = new SeatAdapter(this, seatList, new SeatAdapter.OnSeatSelectedListener() {
            @Override
            public void onSeatSelected(int priceChange, boolean isSelected, String seatName) {
                totalPrice += priceChange;

                if (isSelected) {
                    countSeat++;
                    selectedSeats.add(seatName);
                } else {
                    countSeat--;
                    selectedSeats.remove(seatName);
                }

                totalPriceTextView.setText(totalPrice + "đ");
                txtcountSeat.setText("x" + countSeat);

                String selectedSeatsText = String.join(", ", selectedSeats);
                txtseatName.setText(selectedSeatsText);

                // Log information about the selected seats
                Log.d("SelectedSeats", "Số lượng ghế đã chọn: " + countSeat);
                Log.d("SelectedSeats", "Danh sách ghế đã chọn: " + selectedSeatsText);
            }

        });
        recyclerView.setAdapter(seatAdapter);


    }

    private void loadSeatsFromAPI(String tenRap) {

            String url =Server.getGhe + "?TenRap=" +tenRap;

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
                                    String name = seatObject.getString("tenghe");
                                    boolean isAvailable = seatObject.getInt("trangthai")== 1; // If there's no idve, it's available

                                    // Add the seat to the list
                                    seatList.add(new Seat(name, isAvailable));
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
}
