package com.my.app.controller;

import com.my.app.model.Ingredient;
import com.my.app.model.Pizza;
import com.my.app.repository.IngredientRepository;
import com.my.app.repository.PizzaRepository;
import org.apache.log4j.Logger;
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

    @Autowired
    private IngredientRepository ingredientRepository;

    Logger log = Logger.getLogger(this.getClass());

    @PostMapping("/add")
    public ResponseEntity<?> savePizza(@RequestBody Pizza pizza) {
        try {
            Pizza u = pizzaRepository.findOneByName(pizza.getName());
            if (u == null) {
                pizzaRepository.save(pizza);
                if (pizza.getPizzaid() != null) {
                    log.info("savePizza: " + pizza.getName());
                    return ResponseEntity.ok(pizza);
                }
            } else {
                u.setPrice(pizza.getPrice());
                u.setIngredient(pizza.getIngredient());
                pizzaRepository.save(u);
                if (u.getPizzaid() != null) {
                    log.info("save(Update)Pizza: " + pizza.getName());
                    return ResponseEntity.ok(u);
                }
            }

            return new ResponseEntity<Pizza>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<Pizza>(HttpStatus.BAD_REQUEST);
    }

    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            List<Pizza> pizzas = pizzaRepository.findAll();
            if (pizzas.size() > 0) {
                log.info("findAll: " + pizzas.stream().count());
                return ResponseEntity.ok(pizzas);
            } else {
                return new ResponseEntity<Pizza>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
        log.error(e.getMessage());
        return null;
    }

    }

    @GetMapping("/{name}/ingredient")
    public List<Ingredient> getPizzaIngredient(@PathVariable("name") String name) {
        try {
            log.info("getPizzaIngredient: " + name + " " + pizzaRepository.findOneByName(name).getIngredient());
            return pizzaRepository.findOneByName(name).getIngredient();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @GetMapping("/{name}")
    public Pizza getPizzaByName(@PathVariable("name") String name) {
        try {
            log.info("getPizzaByName: " + name);
            return pizzaRepository.findOneByName(name);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/remove/{id}")
    public void removePizza(@PathVariable("id") Long id) { //to ("id") mozna usnac bo zmienna nazywa sie tak samo
        try {
            int i = pizzaRepository.removeByPizzaid(id);
            log.info("removePizza: id=" + i);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
