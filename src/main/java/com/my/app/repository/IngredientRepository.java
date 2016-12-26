package com.my.app.repository;

import com.my.app.model.Ingredient;
import com.my.app.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Marcin on 26/12/2016.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAll();
    Ingredient save(Ingredient ingredient);
}
