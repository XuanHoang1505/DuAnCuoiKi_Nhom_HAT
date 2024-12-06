package com.example.duan_android.Model;

public class Voucher {
    private int idVoucher;
    private String tenVoucher;
    private int hinhAnh;
    private int soDiemDoi;
    private double soTienGiam;

    public Voucher(int idVoucher, String tenVoucher, int hinhAnh, int soDiemDoi, double soTienGiam) {
        this.idVoucher = idVoucher;
        this.tenVoucher = tenVoucher;
        this.hinhAnh = hinhAnh;
        this.soDiemDoi = soDiemDoi;
        this.soTienGiam = soTienGiam;
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoDiemDoi() {
        return soDiemDoi;
    }

    public void setSoDiemDoi(int soDiemDoi) {
        this.soDiemDoi = soDiemDoi;
    }

    public double getSoTienGiam() {
        return soTienGiam;
    }

    public void setSoTienGiam(double soTienGiam) {
        this.soTienGiam = soTienGiam;
    }
}
