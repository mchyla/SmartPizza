package com.my.app.repository;

import com.my.app.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
