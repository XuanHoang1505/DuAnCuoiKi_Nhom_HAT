package com.example.duan_android.Model;

import java.util.List;

public class lichchieu {
    private String tenrp;
    private List<String> giochieu;

    public lichchieu(String tenrp, List<String> giochieu) {
        this.tenrp = tenrp;
        this.giochieu = giochieu;
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
}
