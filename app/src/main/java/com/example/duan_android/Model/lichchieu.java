package com.example.duan_android.Model;

import java.util.List;

public class lichchieu {
    private String tenrp;
    private List<String> giochieu;
    private List<Integer> idlc;

    public lichchieu(String tenrp, List<String> giochieu, List<Integer> idlc) {
        this.tenrp = tenrp;
        this.giochieu = giochieu;
        this.idlc = idlc;
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