package com.my.app.controller;

import com.my.app.model.Role;
import com.my.app.model.User;
import com.my.app.repository.PersonRepository;
import com.my.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Marcin on 24.11.2016.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> savePerson(@RequestBody User person) {
        person.setRole(Role.ROLE_USER);
        String password = person.getPassword();
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        person.setPassword(password);
        personRepository.save(person);
        if (person.getId() != null) {
            return ResponseEntity.ok(person);
        }
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    //@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<User> people = personRepository.findAll();
        if (people.size() > 0) {
            return ResponseEntity.ok(people);
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/remove/{id}")
    public void removePerson(@PathVariable("id") Long id) { //to ("id") mozna usnac bo zmienna nazywa sie tak samo
        personRepository.removeOne(id);
    }

    @GetMapping(value = "/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = Optional.ofNullable(userRepository.findOneByEmail(auth.getName()));
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/update")
    public void updateUser(@RequestBody User user) {

        User currentUser = userRepository.findOne(user.getId());
        if (currentUser != null) {

            currentUser.setLogin(user.getLogin());
            currentUser.setEmail(user.getEmail());
            currentUser.setPhones(user.getPhones());
            userRepository.save(currentUser);
        } else {
            // no user to update
        }
    }
}
