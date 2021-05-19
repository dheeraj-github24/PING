package com.example.ping02.Model;

public class User {
    private String id;
    private String fname;
    private String ImageURL;

    public User(String id, String fname, String imageURL) {
        this.id = id;
        this.fname = fname;
        this.ImageURL = imageURL;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        this.ImageURL = imageURL;
    }
}
