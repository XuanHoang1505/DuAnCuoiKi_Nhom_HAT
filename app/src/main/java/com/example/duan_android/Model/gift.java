package com.example.duan_android.Model;

public class gift {
    private int imggift;
    private String namegift;
    private int point;

    public gift(int imggift, String namegift, int point) {
        this.imggift = imggift;
        this.namegift = namegift;
        this.point = point;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
