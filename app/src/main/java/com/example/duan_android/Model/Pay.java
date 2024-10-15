package com.example.duan_android.Model;

public class Pay {
    private int resourceImage;
    private String payTitle;

    public Pay(int resourceImage, String payTitle) {
        this.resourceImage = resourceImage;
        this.payTitle = payTitle;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getPayTitle() {
        return payTitle;
    }

    public void setPayTitle(String payTitle) {
        this.payTitle = payTitle;
    }
}
