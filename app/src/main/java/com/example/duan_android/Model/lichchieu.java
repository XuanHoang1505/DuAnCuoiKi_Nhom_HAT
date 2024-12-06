package com.example.duan_android.Model;

import java.util.List;

public class lichchieu {
    private int idrap;
    private String tenrp;
    private List<String> giochieu;
    private List<Integer> idlc;


    public lichchieu(int idrap, String tenrp, List<String> giochieu, List<Integer> idlc) {
        this.idrap = idrap;
        this.tenrp = tenrp;
        this.giochieu = giochieu;
        this.idlc = idlc;
    }

    public int getIdrap() {
        return idrap;
    }

    public void setIdrap(int idrap) {
        this.idrap = idrap;
    }

    public String getTenrp() {
        return tenrp;
    }

    public void setTenrp(String tenrp) {
        this.tenrp = tenrp;
    }

    public List<String> getGiochieu() {
        return giochieu;
    }

    public void setGiochieu(List<String> giochieu) {
        this.giochieu = giochieu;
    }

    public List<Integer> getIdlc() {
        return idlc;
    }

    public void setIdlc(List<Integer> idlc) {
        this.idlc = idlc;
    }
}