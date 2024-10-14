package com.example.duan_android.Model;

public class mygift {
    private int imggift;
    private String namegift;
    private int quantity;

    public int getImggift() {
        return imggift;
    }

    public void setImggift(int imggift) {
        this.imggift = imggift;
    }

    public String getNamegift() {
        return namegift;
    }

    public void setNamegift(String namegift) {
        this.namegift = namegift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public mygift(int imggift, String namegift, int quantity) {
        this.imggift = imggift;
        this.namegift = namegift;
        this.quantity = quantity;
    }
}
