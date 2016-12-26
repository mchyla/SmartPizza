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
    private int houseNumber;
    private int flatNumber;

    @OneToMany
    private List<User> user;

    public Adress() {
    }

    public Adress(String city, String street, int houseNumber, int flatNumber, List<User> user) {
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

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(int flatNumber) {
        this.flatNumber = flatNumber;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
