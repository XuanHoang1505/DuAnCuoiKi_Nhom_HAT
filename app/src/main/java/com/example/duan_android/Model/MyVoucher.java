package com.example.duan_android.Model;

public class MyVoucher {
    private int id;
    private int imggift;
    private String namegift;
    private String discountAmount; // Sửa quantity thành DiscountAmount

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyVoucher(int id, int imggift, String namegift, String discountAmount) {
        this.id = id;
        this.imggift = imggift;
        this.namegift = namegift;
        this.discountAmount = discountAmount;
    }

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

    public String getDiscountAmount() { // Sửa getter cho DiscountAmount
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) { // Sửa setter cho DiscountAmount
        this.discountAmount = discountAmount;
    }

    public MyVoucher(int imggift, String namegift, String discountAmount) { // Sửa constructor
        this.imggift = imggift;
        this.namegift = namegift;
        this.discountAmount = discountAmount;
    }
}
