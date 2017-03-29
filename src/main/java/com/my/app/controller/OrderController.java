package com.my.app.controller;

import com.my.app.model.Ingredient;
import com.my.app.model.OrderPizza;
import com.my.app.model.Pizza;
import com.my.app.model.User;
import com.my.app.repository.IngredientRepository;
import com.my.app.repository.OrderRepository;
import com.my.app.repository.PizzaRepository;
import com.my.app.repository.UserRepository;
import com.my.app.utils.PizzaUtils;
import org.apache.log4j.Logger;
import org.codehaus.groovy.runtime.powerassert.SourceText;
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

import javax.persistence.criteria.Order;
import javax.print.attribute.IntegerSyntax;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.count;

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

    static Logger log = Logger.getLogger(OrderController.class);

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
    public ResponseEntity<?> test() {

        try {
            PizzaUtils pizzaUtils = new PizzaUtils(ingredientRepository, pizzaRepository);
            List<Pizza> listOfPizza = pizzaRepository.findAll();
            List<OrderPizza> listOfUserOrders = returnUserOrders();
            //TODO: Change this to variable from last week to now or smth
            Date date = new Date(2017-1900,02,30,00,34,00);
            Date edate = new Date(2017-1900,02,30,00,36,00);
            List<OrderPizza> dateList = orderRepository.findByDateBetween(date, edate);

            //returnUserOrdersFromStartDateToEndDate(date, edate);

            List<Pizza> listOfAllOrderedPizza = new ArrayList<Pizza>();

            for (OrderPizza p : listOfUserOrders) {
                listOfAllOrderedPizza = Stream.concat(listOfAllOrderedPizza.stream(),
                        p.getPizzaList().stream()).collect(Collectors.toList());
            }

            Set<Pizza> setOfPizza = new HashSet<Pizza>(listOfAllOrderedPizza);

            Map<Pizza, Long> counts =
                    listOfAllOrderedPizza.stream().collect(
                            Collectors.groupingBy(Function.identity(), Collectors.counting()));

            //System.out.println(setOfPizza.size());
            List<Pizza> allOrderedPizzas = setOfPizza.stream().collect(Collectors.toList());

            List<Pizza> listOfNotBoughtPizza = listOfPizza;
            listOfNotBoughtPizza.removeAll(allOrderedPizzas);


            int favoriteClass = getUserFavoritePizza(allOrderedPizzas, counts);

            List<Pizza> pizzaToCoupon = listOfNotBoughtPizza
            .stream()
            .filter(p -> match(p.getDec(), favoriteClass))
            .collect(Collectors.toList());

            log.info("Favorite class: " + favoriteClass);

            log.info("Pizzas to suggest: ");
            for (int i = 0; i < pizzaToCoupon.size(); i++){
                log.info(i + " " + pizzaToCoupon.get(i).getName());
            }

            return new ResponseEntity<Pizza>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Pizza>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/test2")
    public ResponseEntity<?> analysePizzas() {
        try {
            PizzaUtils p = new PizzaUtils(ingredientRepository, pizzaRepository);
           // p.binaryzeIngredients(pizzaRepository.getOne(12L));

            p.checkDistanceFromTemplate();

            return new ResponseEntity<Pizza>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Pizza>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<OrderPizza> returnUserOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findOneByEmail(auth.getName().toString());
        List<OrderPizza> people = currentUser.getOrder();
        return people;
    }

    //public List<OrderPizza> returnUserOrdersFromStartDateToEndDate(Date startDate, Date endDate){
    //    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //    User currentUser = userRepository.findOneByEmail(auth.getName().toString());
    //    return orderRepository.findByDateBetween(startDate);
    //}

    public static boolean match(int a, int b) {
        if (a == b) {
            return true;
        } else return false;
    }


    public static int getUserFavoritePizza(List<Pizza> allBoughtPizzas, Map<Pizza, Long> counts) {

        Set<Long> values = new HashSet<Long>(counts.values());
        boolean isUnique = values.size() == 1;

        if (isUnique == true) {
            Random generator = new Random();
            int i = generator.nextInt(counts.size());
            log.info("Count of pizzas in the order are the same: get random favorite pizza -> "+ allBoughtPizzas.get(i).getDec());
            log.info("Generated random value is: "+i);
            return  allBoughtPizzas.get(i).getDec();
        } else {

            int favoriteClass;
            int amount = 0;
            Pizza favoritePizza = null;
            for (int i = 0; i < allBoughtPizzas.size(); i++) {
                Pizza boughtPizza = allBoughtPizzas.get(i);
                if (amount < counts.get(boughtPizza).intValue()) {
                    amount = counts.get(boughtPizza).intValue();
                    favoritePizza = boughtPizza;
                }
            }

            log.info("Favorite pizza class is: " +favoritePizza.getDec());
            return favoritePizza.getDec();
        }

    }
}
