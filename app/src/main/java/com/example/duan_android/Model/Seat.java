package com.example.duan_android.Model;

public class Seat {
    private int idghe;
    private String name;
    private boolean isAvailable;
    private boolean isSelected; // Trạng thái ghế có được chọn hay không

    public Seat(int idghe, String name, boolean isAvailable) {
        this.idghe = idghe;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public int getIdghe() {
        return idghe;
    }

    public void setIdghe(int idghe) {
        this.idghe = idghe;
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