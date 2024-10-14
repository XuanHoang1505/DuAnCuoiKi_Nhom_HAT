package com.example.duan_android.Model;

public class Seat {
    private String seatName;
    private boolean isAvailable;

    public Seat(String seatName, boolean isAvailable) {
        this.seatName = seatName;
        this.isAvailable = isAvailable;
    }

    public String getSeatName() {
        return seatName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
