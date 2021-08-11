package com.modern_tec.ecommerce.core.models;

public class User {
    protected String email;
    protected String name;
    protected String photoUrl;
    protected String address;


    public User() {
    }


    public User(String email, String name, String photoUrl, String address) {
        this.email = email;
        this.name = name;
        this.photoUrl = photoUrl;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
