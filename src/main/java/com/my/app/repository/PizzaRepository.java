package com.my.app.repository;

import com.my.app.model.Ingredient;
import com.my.app.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Marcin on 26/12/2016.
 */
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

    List<Pizza> findAll();
    Pizza findOneByName(String name);
    @Transactional
    int removeByPizzaid(Long id);
    Pizza findByPizzaid(Long id);
    List<Pizza> findByDecision(int dec);

/*
    @Modifying
    @Transactional
    @Query("UPDATE Pizza p SET p.name = :name, p.ingredient = :ingredient, p.price = :price WHERE p.id = :pizzaId")
    int updatePizza(@Param("pizzaId") Long companyId,
                    @Param("name") String name,
                    @Param("ingredient")List<Ingredient> ingredient,
                    @Param("price")double price);*/
    //Pizza updateByPizzaid(Long id);
}
