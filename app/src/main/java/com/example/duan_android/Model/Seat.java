package com.example.duan_android.Model;

public class Seat {
    private String name;
    private boolean isAvailable;
    private boolean isSelected; // Trạng thái ghế có được chọn hay không

    // Constructor, getter và setter
    public Seat(String name, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}

