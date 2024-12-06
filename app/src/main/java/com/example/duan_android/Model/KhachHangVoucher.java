package com.example.duan_android.Model;

public class KhachHangVoucher extends Voucher{
    private int soLuong;
    private String trangThai;

    public KhachHangVoucher(int idVoucher, String tenVoucher, int hinhAnh, int soDiemDoi, double soTienGiam, int soLuong, String trangThai) {
        super(idVoucher, tenVoucher, hinhAnh, soDiemDoi, soTienGiam);
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
