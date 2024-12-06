package com.example.duan_android.Model;

public class combo {
    private int ID;
    private String title;
    private String info;
    private int image;
    private double price;
    private int soluong;

    public combo(int ID, String title, String info, int image, double price) {
        this.ID = ID;
        this.title = title;
        this.info = info;
        this.image = image;
        this.price = price;
        this.soluong = 0; // Mặc định số lượng là 0
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public int getImage() {
        return image;
    }

    public double getPrice() {
        return price;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
