package com.example.duan_android.Model;

import java.util.Date;
import java.util.List;

public class Movie {
    private int resourceImage;
    private String name;
    private int time;
    private String date;
    private List<String> showTime;
    public Movie(String name, int resourceImage) {
        this.name = name;
        this.resourceImage = resourceImage;
    }

    public Movie(int resourceImage, String name, String date, int time, List<String> showTime) {
        this.resourceImage = resourceImage;
        this.date = date;
        this.time = time;
        this.name = name;
        this.showTime = showTime;

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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getShowTime() {
        return showTime;
    }
    public void setShowTime(List<String> showTime) {
        this.showTime = showTime;
    }
}
