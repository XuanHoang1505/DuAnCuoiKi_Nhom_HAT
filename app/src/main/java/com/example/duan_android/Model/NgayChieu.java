package com.example.duan_android.Model;

public class NgayChieu {
    private int id;
    private String ngaychieu;

    public NgayChieu( String ngaychieu) {
        this.ngaychieu = ngaychieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgaychieu() {
        return ngaychieu;
    }

    public void setNgaychieu(String ngaychieu) {
        this.ngaychieu = ngaychieu;
    }

}