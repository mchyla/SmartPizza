package com.my.app.controller;

import com.my.app.model.Ingredient;
import com.my.app.model.Pizza;
import com.my.app.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> savePizza(@RequestBody Pizza pizza){
        pizzaRepository.save(pizza);
        System.out.println(pizza.getName());
        if(pizza.getPizzaid()!=null){
            return ResponseEntity.ok(pizza);
        }
        return new ResponseEntity<Pizza>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        List<Pizza> people = pizzaRepository.findAll();
        if(people.size()>0){
            return ResponseEntity.ok(people);
        }
        return new ResponseEntity<Pizza>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{name}/ingredient")
    public List<Ingredient> getPizzaIngredient(@PathVariable("name") String name){
        return pizzaRepository.findByName(name).getIngredient();
    }

    @PostMapping("/remove/{id}")
    public void removePizza(@PathVariable("id") Long id){ //to ("id") mozna usnac bo zmienna nazywa sie tak samo
        pizzaRepository.removeByPizzaid(id);
    }
}
