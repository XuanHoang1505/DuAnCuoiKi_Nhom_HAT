package com.example.duan_android.Model;

public class Movie {
    private int resourceImage;
    private String name;

    public Movie(String name, int resourceImage) {
        this.name = name;
        this.resourceImage = resourceImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }
}
