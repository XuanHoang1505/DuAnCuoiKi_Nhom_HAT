package com.example.duan_android.Activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.duan_android.Adapter.SeatAdapter;
import com.example.duan_android.Model.Seat;
import com.example.duan_android.R;

public class BookingActivty extends AppCompatActivity {

    private TextView totalPriceTextView;
    private RecyclerView recyclerView;
    private SeatAdapter seatAdapter;
    private List<Seat> seatList;
    private int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_activty);

        totalPriceTextView = findViewById(R.id.totalPrice);
        recyclerView = findViewById(R.id.recyclerViewSeats);

        // Danh sách ghế với vị trí cụ thể và trạng thái (available hoặc sold)
        seatList = new ArrayList<>();
        // Giả lập danh sách ghế từ A1 tới K12 (khoảng 120 ghế)
        // Trong đó, bạn có thể tuỳ chỉnh để chỉ định ghế đã bán
        for (int i = 0; i < 144; i++) {
            if (i == 10 || i == 20 || i == 50) {  // Giả sử một số ghế đã bán
                seatList.add(new Seat("Ghế " + (i + 1), false)); // Ghế đã bán
            } else {
                seatList.add(new Seat("Ghế " + (i + 1), true));  // Ghế trống
            }
        }

        // Set up RecyclerView với GridLayoutManager (12 cột, giống hình ảnh)
        recyclerView.setLayoutManager(new GridLayoutManager(this, 12));
        seatAdapter = new SeatAdapter(this, seatList, new SeatAdapter.OnSeatSelectedListener() {
            @Override
            public void onSeatSelected(int priceChange) {
                totalPrice += priceChange;
                totalPriceTextView.setText(totalPrice + "đ");
            }
        });
        recyclerView.setAdapter(seatAdapter);
    }
}