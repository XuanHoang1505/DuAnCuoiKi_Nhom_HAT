package com.example.duan_android.Model;

public class combo {
    private int image;
    private String title;
    private String info;
    private double price;

    public combo(int image, String title, String info, double price) {
        this.image = image;
        this.title = title;
        this.info = info;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
