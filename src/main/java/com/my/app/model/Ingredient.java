package com.my.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Marcin on 26/12/2016.
 */
@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private Long ingredient_id;
    private String name;
    private boolean vege;

    public Ingredient() {
    }

    public Ingredient(String name, boolean vege) {
        this.name = name;
        this.vege = vege;
    }

    public Long getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(Long ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVege() {
        return vege;
    }

    public void setVege(boolean vege) {
        this.vege = vege;
    }
}
