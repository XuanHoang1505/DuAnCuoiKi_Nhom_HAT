package com.example.duan_android.Model;

import java.sql.Time;
import java.util.Date;

public class deal {
    private int hinhanh;
    private String tenphim;
    private String rapphim;
    private String phongchieu;
    private String giobatdau;
    private String ngaychieu;

    public deal(int hinhanh, String tenphim, String rapphim, String phongchieu, String giobatdau, String ngaychieu) {
        this.hinhanh = hinhanh;
        this.tenphim = tenphim;
        this.rapphim = rapphim;
        this.phongchieu = phongchieu;
        this.giobatdau = giobatdau;
        this.ngaychieu = ngaychieu;
    }

    public int getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(int hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public String getRapphim() {
        return rapphim;
    }

    public void setRapphim(String rapphim) {
        this.rapphim = rapphim;
    }

    public String getPhongchieu() {
        return phongchieu;
    }

    public void setPhongchieu(String phongchieu) {
        this.phongchieu = phongchieu;
    }

    public String getGiobatdau() {
        return giobatdau;
    }

    public void setGiobatdau(String giobatdau) {
        this.giobatdau = giobatdau;
    }

    public String getNgaychieu() {
        return ngaychieu;
    }

    public void setNgaychieu(String ngaychieu) {
        this.ngaychieu = ngaychieu;
    }
}