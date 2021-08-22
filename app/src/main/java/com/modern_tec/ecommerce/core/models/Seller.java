package com.modern_tec.ecommerce.core.models;

import java.util.List;

public class Seller extends User {
    private String phone;
    private String password;

    public Seller(String email, String name, String photoUrl, List<Address> address, String phone, String password) {
        super(email, name, photoUrl, address);
        this.phone = phone;
        this.password = password;
    }

    public Seller() {
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
