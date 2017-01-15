package com.my.app.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Marcin on 21/12/2016.
 */
@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String houseNumber;
    private String flatNumber;

    @OneToMany
    private List<User> user;

    public Adress() {
    }

    public Adress(String city, String street, String houseNumber, String flatNumber, List<User> user) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.flatNumber = flatNumber;
        this.user = user;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
