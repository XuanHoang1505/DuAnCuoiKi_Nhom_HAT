package com.example.duan_android.Model;

public class Comment {
    private int resourceImage;
    private String preview;

    public Comment(int resourceImage, String preview) {
        this.resourceImage = resourceImage;
        this.preview = preview;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}
