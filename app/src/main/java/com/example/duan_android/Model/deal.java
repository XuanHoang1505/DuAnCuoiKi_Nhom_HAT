package com.example.duan_android.Model;

public class deal {
    private int img;
    private String namemovie;
    private String type;
    private String code;
    private String room;
    private String namecinema;
    private String time;
    private String date;

    public deal(int img, String namemovie, String type, String code, String room, String namecinema, String time, String date) {
        this.img = img;
        this.namemovie = namemovie;
        this.type = type;
        this.code = code;
        this.room = room;
        this.namecinema = namecinema;
        this.time = time;
        this.date = date;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNamemovie() {
        return namemovie;
    }

    public void setNamemovie(String namemovie) {
        this.namemovie = namemovie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getNamecinema() {
        return namecinema;
    }

    public void setNamecinema(String namecinema) {
        this.namecinema = namecinema;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
