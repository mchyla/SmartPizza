package com.my.app.controller;

import com.my.app.model.Adress;
import com.my.app.model.User;
import com.my.app.repository.AdressRepository;
import com.my.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mchyl on 14/01/2017.
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AdressRepository adressRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity addAddress(@RequestBody Adress addressFromForm) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findOneByEmail(auth.getName().toString());
        if (currentUser != null) {
            Adress adress = new Adress();
            adress.setCity(addressFromForm.getCity());
            adress.setStreet(addressFromForm.getStreet());
            adress.setHouseNumber(addressFromForm.getHouseNumber());
            adress.setFlatNumber(addressFromForm.getFlatNumber());
            //adress.getUser().add(currentUser);
            adressRepository.save(adress);
            currentUser.getAdress().add(adress);
            userRepository.save(currentUser);
            return ResponseEntity.ok(adress);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

/*        try {
            Adress u = adressRepository.findByUser(pizza.getName());
            if (u == null) {
                pizzaRepository.save(pizza);
                if (pizza.getPizzaid() != null) {
                    return ResponseEntity.ok(pizza);
                }
            } else {
                u.setPrice(pizza.getPrice());
                u.setIngredient(pizza.getIngredient());
                pizzaRepository.save(u);
                if (u.getPizzaid() != null) {
                    return ResponseEntity.ok(u);
                }
            }

            return new ResponseEntity<Adress>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/

    }


    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<Adress> allAddressList = adressRepository.findAll();
        if (allAddressList.size() > 0) {
            return ResponseEntity.ok(allAddressList);
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("user/all")
    public ResponseEntity<?> findAllAddressOfUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findOneByEmail(auth.getName().toString());

        List<Adress> addressList = currentUser.getAdress();
        if (addressList.size() > 0) {
            return ResponseEntity.ok(addressList);
        }
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
