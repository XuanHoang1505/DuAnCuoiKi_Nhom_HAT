package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_android.Model.Seat;
import com.example.duan_android.R;

import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {

    private List<Seat> seatList;
    private Context context;
    private OnSeatSelectedListener onSeatSelectedListener;

    // Giao diện để xử lý sự kiện chọn ghế
    public interface OnSeatSelectedListener {
        void onSeatSelected(int priceChange);
    }

    public SeatAdapter(Context context, List<Seat> seatList, OnSeatSelectedListener listener) {
        this.context = context;
        this.seatList = seatList;
        this.onSeatSelectedListener = listener;
    }

    @NonNull
    @Override
    public SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seat_item, parent, false);
        return new SeatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SeatViewHolder holder, int position) {
        final Seat seat = seatList.get(position);

        if (!seat.isAvailable()) {
            // Nếu ghế đã bán, hiển thị ghế màu đen và không cho phép chọn
            holder.seatButton.setBackgroundResource(R.drawable.seat_sold);
            holder.seatButton.setEnabled(false);
        } else {
            // Nếu ghế trống, hiển thị ghế màu trắng và viền đen
            holder.seatButton.setBackgroundResource(R.drawable.seat_available);
            holder.seatButton.setEnabled(true );

            holder.seatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.isSelected) {
                        holder.seatButton.setBackgroundResource(R.drawable.seat_available); // Trở lại ghế trống
                        holder.isSelected = false   ;
                        onSeatSelectedListener.onSeatSelected(-60000); // Trừ tiền khi bỏ chọn
                    } else {
                        holder.seatButton.setBackgroundResource(R.drawable.seat_selected); // Đổi thành màu cam khi chọn
                        holder.isSelected = true;
                        onSeatSelectedListener.onSeatSelected(60000); // Tăng tiền khi chọn ghế
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        Button seatButton;
        boolean isSelected = false;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seatButton = itemView.findViewById(R.id.seatButton);
        }
    }
}
