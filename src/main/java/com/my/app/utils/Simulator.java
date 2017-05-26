package com.my.app.utils;

import com.my.app.controller.OrderController;
import com.my.app.model.OrderPizza;
import com.my.app.model.Pizza;
import com.my.app.model.User;
import com.my.app.repository.IngredientRepository;
import com.my.app.repository.PizzaRepository;
import com.my.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by mchyl on 19/04/2017.
 */
@RestController
@RequestMapping("/api/simulate")
public class Simulator {

    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(OrderController.class);

    //Creating fe. 100xUsers
    //Random buying pizzas (x100)
    //Analyze bought pizzas -> api/order/test
    //set random value to buy favorite like pizza or not
    //create path for buying favorite like pizza -> change cost to -10%
    //create next 100 boughts
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private UserRepository userRepository;

    private Random r = new Random();

    //http://theweek.com/articles/483784/americas-pizza-obsession-by-numbers - 26.04.2017
    //3000 000 000
    //https://pl.wikipedia.org/wiki/Demografia_Stan%C3%B3w_Zjednoczonych
    //307 212 123
    //3 000 000 000 / 307 212 123 = 9.7 - wynik rocznie / 12 = 0,8 pizzy mounthly /4 = 0,2 pizzy weekly

    @RequestMapping("/start")
    public void simulate() {

        int howMuchClients = 1000;
        int howMuchWeeks = 52;//12; //3months //52 - year

        List<Pizza> pizzaAllList = pizzaRepository.findAll();
        float totalCash = 0;

        LinkedList<User> userLinkedList = new LinkedList<>();
        for (int i = 0; i < howMuchClients; i++) {
            User user = new User();
            user.setOrder(new LinkedList<OrderPizza>());
            userLinkedList.add(user);
        }

        for (int i = 0; i < userLinkedList.size(); i++) {

            for (int j = 0; j < howMuchWeeks; j++) {
                boolean buyPizza = calculateChance(r, 0.20f);

                if (buyPizza) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, pizzaAllList.size());
                    OrderPizza orderPizza = new OrderPizza();
                    List<Pizza> pizzaBoughtList = new LinkedList<>();
                    pizzaBoughtList.add(pizzaAllList.get(randomNum));
                    orderPizza.setPizzaList(pizzaBoughtList);
                    orderPizza.setPrice(pizzaAllList.get(0).getPrice());
                    userLinkedList.get(i).getOrder().add(orderPizza);
                    totalCash += orderPizza.getPrice();
                }
            }
        }

        PizzaUtils pizzaUtils = new PizzaUtils(ingredientRepository, pizzaRepository, userRepository);

        double totalCashPromo = 0;

        for (int i = 0; i < userLinkedList.size(); i++) {

            for (int j = 0; j < howMuchWeeks; j++) {
                boolean buyPizza = calculateChance(r, 0.30f);

                if (buyPizza) {
                    OrderPizza orderPizza = new OrderPizza();
                    List<Pizza> pizzaBoughtList = new LinkedList<>();
                    pizzaBoughtList.add(pizzaUtils.test3());
                    orderPizza.setPizzaList(pizzaBoughtList);
                    orderPizza.setPrice(pizzaAllList.get(0).getPrice() - 0.2 * pizzaAllList.get(0).getPrice());
                    userLinkedList.get(i).getOrder().add(orderPizza);
                    totalCashPromo += orderPizza.getPrice();
                }
            }
        }

        log.info("Without promotions: " + totalCash);
        log.info("With promotions: " + totalCashPromo);

    }

    public boolean calculateChance(Random r, float percentageChance) {
        float chance = r.nextFloat();
        return chance <= percentageChance;
    }

}
