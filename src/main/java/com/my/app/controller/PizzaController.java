package com.my.app.controller;

import com.my.app.model.Ingredient;
import com.my.app.model.Pizza;
import com.my.app.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Marcin on 26/12/2016.
 */
@RestController
@RequestMapping("/api/pizza")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @PostMapping("/add")
    public ResponseEntity<?> savePizza(@RequestBody Pizza pizza) {


        try {
            Pizza u = pizzaRepository.findOneByName(pizza.getName());
            if (u == null) {
                System.out.println("wchodz w ifa");
                pizzaRepository.save(pizza);
                if (pizza.getPizzaid() != null) {
                    return ResponseEntity.ok(pizza);
                }
            } else {
                System.out.println("wchodze w else ");
                u.setPrice(pizza.getPrice());
                u.setIngredient(pizza.getIngredient());
                pizzaRepository.save(u);
                if (u.getPizzaid() != null) {
                    return ResponseEntity.ok(u);
                }
            }

            return new ResponseEntity<Pizza>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<Pizza>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<Pizza> people = pizzaRepository.findAll();
        if (people.size() > 0) {
            return ResponseEntity.ok(people);
        }
        return new ResponseEntity<Pizza>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{name}/ingredient")
    public List<Ingredient> getPizzaIngredient(@PathVariable("name") String name) {
        return pizzaRepository.findOneByName(name).getIngredient();
    }

    @GetMapping("/{name}")
    public Pizza getPizzaByName(@PathVariable("name") String name) {
        return pizzaRepository.findOneByName(name);
    }

    @DeleteMapping("/remove/{id}")
    public void removePizza(@PathVariable("id") Long id) { //to ("id") mozna usnac bo zmienna nazywa sie tak samo
       int i =  pizzaRepository.removeByPizzaid(id);
        System.out.println(i);
    }
}
