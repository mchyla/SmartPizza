package com.my.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Marcin on 24.11.2016.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String login;
    private String password;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    private int phones;

    @OneToMany
    private List<Adress> adress;

    @OneToMany
    private List<OrderPizza> order;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }


    public List<Adress> getAdress() {
        return adress;
    }

    public void setAdress(List<Adress> adress) {
        this.adress = adress;
    }

    public List<OrderPizza> getOrder() {
        return order;
    }

    public void setOrder(List<OrderPizza> order) {
        this.order = order;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhones() {
        return phones;
    }

    public void setPhones(int phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phones=" + phones +
                ", adress=" + adress +
                ", order=" + order +
                ", role=" + role +
                '}';
    }
}
