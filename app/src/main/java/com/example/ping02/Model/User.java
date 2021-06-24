package com.example.ping02.Model;

public class User {
    private String Id;
    private String Firstname;
    private String Lastname;
    private String ImageURL;
    private String Email;
    private String Designation;

    public User(String id, String Firstname,String Lastname, String imageURL, String Email, String designation) {
        this.Id = id;
        this.Firstname = Firstname;
        this.Lastname=Lastname;
        this.ImageURL = imageURL;
        this.Email=Email;
        this.Designation=designation;
    }

    public User() {

    }

    public String getid() {
        return Id;
    }

    public void setid(String id) {
        this.Id = id;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String Firstname) {
        this.Firstname = Firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        this.ImageURL = imageURL;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
}