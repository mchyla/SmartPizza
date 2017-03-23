package com.my.app.controller;

import com.my.app.model.OrderPizza;
import com.my.app.model.Pizza;
import com.my.app.model.User;
import com.my.app.repository.IngredientRepository;
import com.my.app.repository.OrderRepository;
import com.my.app.repository.PizzaRepository;
import com.my.app.repository.UserRepository;
import com.my.app.utils.PizzaUtils;
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

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

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
    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    PizzaRepository pizzaRepository;


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
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findOneByEmail(auth.getName().toString());
        List<OrderPizza> people = currentUser.getOrder();*/
        List<OrderPizza> people = returnUserOrders();

        if (people.size() > 0) {
            return ResponseEntity.ok(people);
        }
        return new ResponseEntity<Pizza>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){

        try {
            PizzaUtils pizzaUtils = new PizzaUtils(ingredientRepository);
            List<Pizza> listOfPizza = pizzaRepository.findAll();
            List<OrderPizza> listOfUserOrders = returnUserOrders();

            List<Pizza> listOfAllOrderedPizza = new ArrayList<Pizza>();

            for(OrderPizza p: listOfUserOrders){
               listOfAllOrderedPizza = Stream.concat(listOfAllOrderedPizza.stream(),
                                                    p.getPizzaList().stream()).collect(Collectors.toList());
            }

            Map<Pizza, Long> counts =
                    listOfAllOrderedPizza.stream().collect(
                            Collectors.groupingBy(Function.identity(), Collectors.counting()));

/*            List<Map<Pizza, Long>> orderAnalysis = new ArrayList<>();

            for (OrderPizza p: listOfUserOrders) {
                listOfPizza.removeAll(p.getPizzaList());

                Map<Pizza, Long> counts =
                        p.getPizzaList().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                orderAnalysis.add(counts);
            }


            System.out.println(orderAnalysis.get(0).keySet());*/

            if(listOfPizza.size() > 0){

            } else {

            }

            //List<Integer> binPizza1 = pizzaUtils.binaryzeIngredients(pizzaRepository.getOne(4L));
            //List<Integer> binPizza2 = pizzaUtils.binaryzeIngredients(pizzaRepository.getOne(6L));
            //System.out.println("Pizza 1 "+binPizza1);
            //System.out.println("Pizza 2 "+binPizza2);
            //System.out.println(pizzaUtils.checkDistance(binPizza1, binPizza2));

            return new ResponseEntity<Pizza>(HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<Pizza>(HttpStatus.BAD_REQUEST);
        }

    }


    public List<OrderPizza> returnUserOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findOneByEmail(auth.getName().toString());
        List<OrderPizza> people = currentUser.getOrder();
        return people;
    }
}
