package com.modern_tec.ecommerce.core.models;

import java.io.Serializable;

public class Address implements Serializable {
    private String addressId;
    private String addressName;
    private String city;
    private String postalCode;
    private String phone;

    public Address() {
    }

    public Address(String addressId, String addressName, String city, String postalCode, String phone) {
        this.addressId = addressId;
        this.addressName = addressName;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }
}
