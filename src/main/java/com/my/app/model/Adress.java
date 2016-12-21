package com.my.app.model;

import javax.persistence.*;

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


}
