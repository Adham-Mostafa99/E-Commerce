package com.modern_tec.ecommerce.core.models;

import java.util.List;

public class User {
    protected String email;
    protected String name;
    protected String photoUrl;
    private List<String> favorites;
    protected List<Address> address;


    public User() {
    }

    public User(String email, String name, String photoUrl, List<Address> address) {
        this.email = email;
        this.name = name;
        this.photoUrl = photoUrl;
        this.address = address;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


    public String getPhotoUrl() {
        return photoUrl;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


}
