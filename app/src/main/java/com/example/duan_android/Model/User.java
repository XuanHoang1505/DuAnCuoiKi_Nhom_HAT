package com.example.duan_android.Model;

public class User {
    private String id;
    private String ten;
    private String email;
    private String matkhau;
    private String diemthuong;

    public User(String id, String ten, String email, String matkhau, String diemthuong) {
        this.id = id;
        this.ten = ten;
        this.email = email;
        this.matkhau = matkhau;
        this.diemthuong = diemthuong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public String getDiemthuong() {
        return diemthuong;
    }

    public void setDiemthuong(String diemthuong) {
        this.diemthuong = diemthuong;
    }
}