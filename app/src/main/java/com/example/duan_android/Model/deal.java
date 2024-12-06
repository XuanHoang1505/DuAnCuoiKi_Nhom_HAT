package com.example.duan_android.Model;

import java.sql.Time;
import java.util.Date;

public class deal {
    private int idhd;
    private int hinhanh;
    private String tenphim;
    private String rapphim;
    private String phongchieu;
    private String giobatdau;
    private String ngaychieu;
    private String ghe;
    private String tencombo;
    private int idve;
    private int diemthuong;
    private double tongtien;

    public deal(int idhd, int hinhanh, String tenphim, String rapphim, String phongchieu, String giobatdau, String ngaychieu) {
        this.idhd = idhd;
        this.hinhanh = hinhanh;
        this.tenphim = tenphim;
        this.rapphim = rapphim;
        this.phongchieu = phongchieu;
        this.giobatdau = giobatdau;
        this.ngaychieu = ngaychieu;
    }

    public deal(int hinhanh, String tenphim, String rapphim, String phongchieu, String giobatdau, String ngaychieu, String ghe, String tencombo,int idve,int diemthuong,double tongtien) {
        this.hinhanh = hinhanh;
        this.tenphim = tenphim;
        this.rapphim = rapphim;
        this.phongchieu = phongchieu;
        this.giobatdau = giobatdau;
        this.ngaychieu = ngaychieu;
        this.ghe = ghe;
        this.tencombo = tencombo;
        this.idve=idve;
        this.diemthuong=diemthuong;
        this.tongtien = tongtien;
    }


    public int getIdhd() {
        return idhd;
    }

    public void setIdhd(int idhd) {
        this.idhd = idhd;
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

    public String getGhe() {
        return ghe;
    }

    public void setGhe(String ghe) {
        this.ghe = ghe;
    }

    public String getTencombo() {
        return tencombo;
    }

    public void setTencombo(String tencombo) {
        this.tencombo = tencombo;
    }

    public int getIdve() {
        return idve;
    }

    public void setIdve(int idve) {
        this.idve = idve;
    }

    public int getDiemthuong() {
        return diemthuong;
    }

    public void setDiemthuong(int diemthuong) {
        this.diemthuong = diemthuong;
    }

    public double getTongtien() {
        return tongtien;
    }

    public void setTongtien(double tongtien) {
        this.tongtien = tongtien;
    }
}