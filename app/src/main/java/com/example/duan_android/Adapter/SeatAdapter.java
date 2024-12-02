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

    public interface OnSeatSelectedListener {
        void onSeatSelected(int priceChange, boolean isSelected, String seatName); // Thêm tên ghế vào phương thức
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
        holder.updateSeatAppearance(seat.isAvailable());

        holder.seatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!seat.isAvailable()) return;

                holder.isSelected = !holder.isSelected;
                holder.updateSeatAppearance(seat.isAvailable());

                if (onSeatSelectedListener != null) {
                    int priceChange = holder.isSelected ? 60000 : -60000;
                    onSeatSelectedListener.onSeatSelected(priceChange, holder.isSelected, seat.getName()); // Truyền tên ghế
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public void updateSeatList(List<Seat> newSeatList) {
        this.seatList.clear();
        this.seatList.addAll(newSeatList);
        notifyDataSetChanged();
    }

    public static class SeatViewHolder extends RecyclerView.ViewHolder {
        Button seatButton;
        boolean isSelected = false;

        public SeatViewHolder(@NonNull View itemView) {
            super(itemView);
            seatButton = itemView.findViewById(R.id.seatButton);
        }

        public void updateSeatAppearance(boolean isAvailable) {
            if (!isAvailable) {
                seatButton.setBackgroundResource(R.drawable.seat_sold);
                seatButton.setEnabled(false);
            } else if (isSelected) {
                seatButton.setBackgroundResource(R.drawable.seat_selected);
                seatButton.setEnabled(true);
            } else {
                seatButton.setBackgroundResource(R.drawable.seat_available);
                seatButton.setEnabled(true);
            }
        }
    }
}
