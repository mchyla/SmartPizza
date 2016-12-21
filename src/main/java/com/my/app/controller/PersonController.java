package com.my.app.controller;

import com.my.app.model.User;
import com.my.app.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Marcin on 24.11.2016.
 */
@RestController
@RequestMapping("/user")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/add")
    public ResponseEntity<?> savePerson(@RequestBody User person){
        personRepository.save(person);
        System.out.println(person.getLogin());
        if(person.getId()!=null){
            return ResponseEntity.ok(person);
        }
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        List<User> people = personRepository.findAll();
        if(people.size()>0){
            return ResponseEntity.ok(people);
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/remove/{id}")
    public void removePerson(@PathVariable("id") Long id){ //to ("id") mozna usnac bo zmienna nazywa sie tak samo
        personRepository.removeOne(id);
    }
}
