package com.my.app.controller;

import com.my.app.model.Ingredient;
import com.my.app.model.User;
import com.my.app.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Marcin on 26/12/2016.
 */
@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;

    @PostMapping("/add")
    public ResponseEntity<?> saveIngredient(@RequestBody  Ingredient ingredient) {
        System.out.println(ingredient.isVege());
        ingredientRepository.save(ingredient);
        if (ingredient.getIngredient_id() != null) {
            return ResponseEntity.ok(ingredient);
        }
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        if(ingredientList.size()>0){
            return ResponseEntity.ok(ingredientList);
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
