package com.my.app.controller;

import com.my.app.model.OrderPizza;
import com.my.app.model.Pizza;
import com.my.app.model.User;
import com.my.app.repository.OrderRepository;
import com.my.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Marcin on 30/12/2016.
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;

    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @RequestMapping("/add")
    public ResponseEntity<?> savePizza(@RequestBody OrderPizza order) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User currentUser = userRepository.findOneByEmail(auth.getName().toString());
            OrderPizza orderPizza = new OrderPizza();

            orderPizza.setAdress(order.getAdress());
            orderPizza.setPizzaList(order.getPizzaList());
            orderPizza.setPrice(order.getPrice());
            orderPizza.setDate(order.getDate());

            System.out.println(orderPizza.toString());
            System.out.println(order.toString());



            currentUser.getOrder().add(orderPizza);
            orderRepository.save(orderPizza);
            userRepository.save(currentUser);

            return new ResponseEntity<OrderPizza>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<OrderPizza>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findOneByEmail(auth.getName().toString());
        List<OrderPizza> people = currentUser.getOrder();
        if (people.size() > 0) {
            return ResponseEntity.ok(people);
        }
        return new ResponseEntity<Pizza>(HttpStatus.NO_CONTENT);
    }
}
