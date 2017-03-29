package com.my.app.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Marcin on 26/12/2016.
 */
@Entity
public class Pizza {

    @Id
    @GeneratedValue
    private Long pizzaid;

    @Column(unique = true)
    private String name;

    @ManyToMany()
    @JoinTable(name="PIZZA_INGREDIENTS",
            joinColumns={@JoinColumn(name="PIZZA_ID")},
            inverseJoinColumns={@JoinColumn(name="INGREDIENT_ID")})
    private List<Ingredient> ingredient;

    private int decision;

    private double price;


    public Pizza() {
    }

    public Pizza(List<Ingredient> ingredientList, double price, String name) {
        this.ingredient = ingredientList;
        this.price = price;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPizzaid() {
        return pizzaid;
    }

    public void setPizzaid(Long pizzaid) {
        this.pizzaid = pizzaid;
    }

    public List<Ingredient> getIngredient() {
        return ingredient;
    }

    public void setIngredient(List<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDec() {
        return decision;
    }

    public void setDec(int dec) {
        this.decision = dec;
    }
}
