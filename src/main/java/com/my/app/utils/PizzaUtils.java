package com.my.app.utils;

import com.my.app.controller.OrderController;
import com.my.app.model.Ingredient;
import com.my.app.model.OrderPizza;
import com.my.app.model.Pizza;
import com.my.app.model.User;
import com.my.app.repository.IngredientRepository;
import com.my.app.repository.PizzaRepository;
import com.my.app.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Marcin on 16.03.2017.
 */
public class PizzaUtils {

    IngredientRepository ingredientRepository;
    PizzaRepository pizzaRepository;
    UserRepository userRepository;

    public PizzaUtils(IngredientRepository ingredientRepository, PizzaRepository pizzaRepository, UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
        this.userRepository = userRepository;
    }

    public PizzaUtils(IngredientRepository ingredientRepository, PizzaRepository pizzaRepository) {
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
    }
    //List<Ingredient> ingredientList = ingredientRepository.findAll();

    public List<Integer> binaryzeIngredients(Pizza pizza) {

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        List<String> allIngredientsNames = new ArrayList<>();

        List<String> pizzaIngredients = new ArrayList<>();

        for (Ingredient i : ingredientList) {
            allIngredientsNames.add(i.getName());
        }

        for (Ingredient i : pizza.getIngredient()) {
            pizzaIngredients.add(i.getName());
        }

        //size need to be equal
/*        if(pizzaIngredients.size()!=allIngredientsNames.size()){
            int difference = allIngredientsNames.size() - pizzaIngredients.size();
            for(int i=0; i<difference; i++){
                pizzaIngredients.add("");
            }
        }*/
        //sort is needed after add empty elements representing lack of ingredient
        Collections.sort(allIngredientsNames);
        Collections.sort(pizzaIngredients);
        List<Integer> binaryList = new ArrayList<Integer>(Collections.nCopies(allIngredientsNames.size(), 0));
        String binaryResult = "";
        for (int j = 0; j < pizzaIngredients.size(); j++) {
            for (int i = 0; i < allIngredientsNames.size(); i++) {
                if (pizzaIngredients.get(j).equals(allIngredientsNames.get(i))) {
                    //binaryResult+="1";
                    binaryList.set(i, 1);
                    continue;
                } else {
                    //binaryResult+="0";
                }
            }
        }
/*        for(int i=0; i<ingredientList.size();i++){
            if(pizzaIngredients.get(i).equals(allIngredientsNames.get(i))){
                binaryResult+="1";
            }else{
                binaryResult+="0";
            }
        }*/

        return binaryList;
    }

    public static int countOnes(String binaryResult) {
        int result = 0;
        char[] tab = binaryResult.toCharArray();
        for (char znak : tab) {
            if (znak == '1')
                result += 1;
        }
        return result;
    }

    public double checkDistance(List<Integer> ingredientsOfPizzaA, List<Integer> ingredientsOfPizzaB) {
        double result = 0;

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        //Collections.sort(ingredientList);
        Collections.sort(ingredientList, new Comparator<Ingredient>() {
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });


        for (int i = 0; i < ingredientsOfPizzaA.size(); i++) {
       /* for(char i : pizzaAIngredients){*/
            //System.out.println(ingredientsOfPizzaA.get(i) +"-"+ ingredientsOfPizzaB.get(i) +"*"+ ingredientList.get(i).getWeight());
            result += Math.abs(ingredientsOfPizzaA.get(i) - ingredientsOfPizzaB.get(i)) * ingredientList.get(i).getWeight();
        }

        return result;
    }

    public void checkDistanceFromTemplate() {
        Logger log = Logger.getLogger(PizzaUtils.class);
/*      String wiejskiGangster = "001001010000000000000100000001000100000100";
        String kowalSwinke = "001001000000000000000000100001000100000000";
        String smieszna = "000001000000000000100000100001000110000000";
        String ryba = "000000000000100010000000000001000100000010";
        String wege = "000001000000010001000001110001000100000000"*/
        ;

        String wiejskiGangster = "000000000000000100000010000000000000000010";
        String kowalSwinke = "001000000000000010001000100001000000100000";
        //String smieszna         = "000001000000000000100000100001000110000000";
        String ryba = "000000110000100010000000110001000100000010";
        String wege = "000001000000010001000001110001000100000000";


        //String wiejskiGangster = "001001010000000000000100000001000100000100";
        //String wiejskiGangster = "001001010000000000000100000001000100000100";
        char[] wiejskiGangsterCharrArray = wiejskiGangster.toCharArray();
        char[] kowalSwinkeCharrArray = kowalSwinke.toCharArray();
        //char[] smiesnaCharrArray = smieszna.toCharArray();
        char[] rybaCharrArray = ryba.toCharArray();
        char[] wegeCharrArray = wege.toCharArray();


        List<Integer> wiejskiGangsterBinaryzedIngredientList = charrArray2IntegerList(wiejskiGangsterCharrArray);
        List<Integer> kowalSwinkeBinaryzedIngredientList = charrArray2IntegerList(kowalSwinkeCharrArray);
        //List<Integer> smiesznaBinaryzedIngredientList = charrArray2IntegerList(smiesnaCharrArray);
        List<Integer> rybaBinaryzedIngredientList = charrArray2IntegerList(rybaCharrArray);
        List<Integer> wegeBinaryzedIngredientList = charrArray2IntegerList(wegeCharrArray);

        List<Pizza> pizzas = pizzaRepository.findAll();

        //  List<List<Double>> listOfAllResultsOfAllPizzas = new ArrayList<>();

        List<Integer> newDec1List = new ArrayList<>(); //= charrArray2IntegerList("001001010000000000000100000001000100000100".toCharArray());
        List<Integer> newDec2List = new ArrayList<>(); //= charrArray2IntegerList("000001000000000000100000100001000110000000".toCharArray());
        List<Integer> newDec3List = new ArrayList<>(); //= charrArray2IntegerList("000001000000000000100000100001000110000000".toCharArray());
        List<Integer> newDec4List = new ArrayList<>(); //= charrArray2IntegerList("000000000000100010000000000001000100000010".toCharArray());
        // List<Integer>  newDec5List = new ArrayList<>(); //= charrArray2IntegerList("000001000000010001000001110001000100000000".toCharArray());

        double distanceFromTemplate1 = 10.0;
        double distanceFromTemplate2 = 10.0;
        double distanceFromTemplate3 = 10.0;
        double distanceFromTemplate4 = 10.0;

        //1st loop of algorithm
        for (int i = 0; i < pizzas.size(); i++) {
            Pizza pizza = pizzas.get(i);
            log.info("The pizza analysis started: " + pizza.getName());
            List<Double> listOfResults = new ArrayList<>();
            listOfResults.add(checkDistance(binaryzeIngredients(pizza), wiejskiGangsterBinaryzedIngredientList));
            listOfResults.add(checkDistance(binaryzeIngredients(pizza), kowalSwinkeBinaryzedIngredientList));
            //listOfResults.add(checkDistance(binaryzeIngredients(pizza), smiesznaBinaryzedIngredientList));
            listOfResults.add(checkDistance(binaryzeIngredients(pizza), rybaBinaryzedIngredientList));
            listOfResults.add(checkDistance(binaryzeIngredients(pizza), wegeBinaryzedIngredientList));


            log.info(new Date()
                    + " [" + pizza.getName() + "]"
                    + " [1dec]: " + round(listOfResults.get(0), 2)
                    + " [2dec]: " + round(listOfResults.get(1), 2)
                    + " [3dec]: " + round(listOfResults.get(2), 2)
                    + " [4dec]: " + round(listOfResults.get(3), 2));
            //+ " [5dec]: " + round(listOfResults.get(4), 2));


            log.info("Min distance from templates: " + round(listOfResults.stream().reduce(Double::min).get(), 2));

            log.info("Maxdistance from templates: " + round(listOfResults.stream().reduce(Double::max).get(), 2));
            log.info("Index of minimal distance: " + listOfResults.indexOf(Collections.min(listOfResults)));
            int dec = 1 + listOfResults.indexOf(Collections.min(listOfResults));


            log.info("Classification result: " + dec);
            log.info("Pizza analysis finished! ");

            log.info("Set decision for:" + pizza.getName());
            pizza.setDec(dec);
            pizzaRepository.save(pizza);
            log.info("Successufful: " + pizza.getName() + " now have decision " + dec);

            listOfResults.stream().forEach(distance -> log.info(new Date()
                    + " [" + pizza.getName() + "] [1dec]: " + round(distance, 2)
                    + " [2dec] " + round(distance, 2)
                    + " [3dec] " + round(distance, 2)
                    + " [4dec] " + round(distance, 2)
                    + " [5dec] " + round(distance, 2)));

            switch (dec) {
                case 1:
                    if (listOfResults.get(0) < distanceFromTemplate1) {
                        newDec1List = binaryzeIngredients(pizza);
                        log.info("New center for class 1 found!");
                    }
                    break;
                case 2:
                    if (listOfResults.get(1) < distanceFromTemplate2) {
                        newDec2List = binaryzeIngredients(pizza);
                        log.info("New center for class 2 found!");
                    }
                    break;
                case 3:
                    if (listOfResults.get(2) < distanceFromTemplate3) {
                        newDec3List = binaryzeIngredients(pizza);
                        log.info("New center for class 3 found!");
                    }
                    break;
                case 4:
                    if (listOfResults.get(3) < distanceFromTemplate4) {
                        newDec4List = binaryzeIngredients(pizza);
                        log.info("New center for class 4 found!");
                    }
                    break;
            }

        }

        //next loops
        for (int x = 0; x < 11; x++) {
            for (int i = 0; i < pizzas.size(); i++) {
                Pizza pizza = pizzas.get(i);
                log.info("The pizza analysis started: " + pizza.getName());
                List<Double> listOfResults = new ArrayList<>();
                if (newDec1List.size() > 0) listOfResults.add(checkDistance(binaryzeIngredients(pizza), newDec1List));
                //listOfResults.add(checkDistance(binaryzeIngredients(pizza), kowalSwinkeBinaryzedIngredientList));
                if (newDec2List.size() > 0) listOfResults.add(checkDistance(binaryzeIngredients(pizza), newDec2List));
                if (newDec3List.size() > 0) listOfResults.add(checkDistance(binaryzeIngredients(pizza), newDec3List));
                if (newDec4List.size() > 0) listOfResults.add(checkDistance(binaryzeIngredients(pizza), newDec4List));


                int dec = 1 + listOfResults.indexOf(Collections.min(listOfResults));

                pizza.setDec(dec);
                pizzaRepository.save(pizza);
                log.info("Successufful: " + pizza.getName() + " now have decision " + dec);
                switch (dec) {
                    case 1:
                        if (listOfResults.get(0) < distanceFromTemplate1) {
                            newDec1List = binaryzeIngredients(pizza);
                            //  log.info("New center for class 1 found!");
                        }
                        break;
                    case 2:
                        if (listOfResults.get(1) < distanceFromTemplate2) {
                            newDec2List = binaryzeIngredients(pizza);
                            // log.info("New center for class 2 found!");
                        }
                        break;
                    case 3:
                        if (listOfResults.get(2) < distanceFromTemplate3) {
                            newDec3List = binaryzeIngredients(pizza);
                            // log.info("New center for class 3 found!");
                        }
                        break;
                    case 4:
                        if (listOfResults.get(3) < distanceFromTemplate4) {
                            newDec4List = binaryzeIngredients(pizza);
                            // log.info("New center for class 4 found!");
                        }
                        break;
/*                case 5:
                    if(listOfResults.get(4) < distanceFromTemplate4){
                        newDec4List = binaryzeIngredients(pizza);
                        System.out.println(5);
                    }
                    break;*/
                }
            }
        }

        log.info(newDec1List.toString().replace(", ", ""));
        log.info(newDec2List.toString().replace(", ", ""));
        log.info(newDec3List.toString().replace(", ", ""));
        log.info(newDec4List.toString().replace(", ", ""));
    }

    public int checkDistanceForNewOne(List<Integer> listOfDecisions, List<OrderPizza> orders) {

        List<Pizza> listOfPizzasFromOpositeClasses = new ArrayList<>();
        int seconndFavoriteClass = 0;
        for (int i = 0; i < listOfDecisions.size(); i++) {
            listOfPizzasFromOpositeClasses.addAll(pizzaRepository.findByDecision(listOfDecisions.get(i)));
        }

        Set<Pizza> setOfPizza = new HashSet<Pizza>();
        for (int i = 0; i < orders.size(); i++) {
            setOfPizza.addAll(orders.get(i).getPizzaList());
        }

        List<Pizza> allOrderedPizzas = setOfPizza.stream().collect(Collectors.toList());
        listOfPizzasFromOpositeClasses.removeAll(allOrderedPizzas);
        double distance = 99999;

        for (int i = 0; i < allOrderedPizzas.size(); i++) {
            for (int j = 0; j < listOfPizzasFromOpositeClasses.size(); j++) {
                double distanceFromOneToAnotherClassPizza =
                        checkDistance(binaryzeIngredients(allOrderedPizzas.get(i)),
                        binaryzeIngredients(listOfPizzasFromOpositeClasses.get(j)));

                if(distanceFromOneToAnotherClassPizza < distance){
                    seconndFavoriteClass = listOfPizzasFromOpositeClasses.get(j).getDec();
                }
            }
        }
        return seconndFavoriteClass;
    }

    public static List<Integer> charrArray2IntegerList(char[] tamplatePizza) {
        List<Integer> templateList = new ArrayList<>();
        for (char i : tamplatePizza) {
            templateList.add(Character.getNumericValue(i));
        }
        return templateList;
    }

    public List<Integer> binaryzeIngredientsList(List<Ingredient> listOfIngredientsToBynaryze) {

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        List<String> allIngredientsNames = new ArrayList<>();

        List<String> pizzaIngredients = new ArrayList<>();

        for (Ingredient i : ingredientList) {
            allIngredientsNames.add(i.getName());
        }

        for (Ingredient i : listOfIngredientsToBynaryze) {
            pizzaIngredients.add(i.getName());
        }

        //sort is needed after add empty elements representing lack of ingredient
        Collections.sort(allIngredientsNames);
        Collections.sort(pizzaIngredients);
        List<Integer> binaryList = new ArrayList<Integer>(Collections.nCopies(allIngredientsNames.size(), 0));
        String binaryResult = "";
        for (int j = 0; j < pizzaIngredients.size(); j++) {
            for (int i = 0; i < allIngredientsNames.size(); i++) {
                if (pizzaIngredients.get(j).equals(allIngredientsNames.get(i))) {
                    //binaryResult+="1";
                    binaryList.set(i, 1);
                    continue;
                } else {
                    //binaryResult+="0";
                }
            }
        }

        return binaryList;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public Pizza test3() {
        try {
            PizzaUtils pizzaUtils = new PizzaUtils(ingredientRepository, pizzaRepository, userRepository);
            List<Pizza> listOfPizza = pizzaRepository.findAll();
            List<OrderPizza> listOfUserOrders = returnUserOrders();
            //TODO: Change this to variable from last week to now or smth
            Date date = new Date(2017-1900,02,30,00,34,00);
            Date edate = new Date(2017-1900,10,30,00,36,00);
           // List<OrderPizza> dateList = OrderController.orderRepository.findByDateBetween(date, edate);

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


            int favoriteClass = OrderController.getUserFavoritePizza(allOrderedPizzas, counts);

            List<Pizza> pizzaToCoupon = listOfNotBoughtPizza
                    .stream()
                    .filter(p -> OrderController.match(p.getDec(), favoriteClass))
                    .collect(Collectors.toList());

            //log.info("Favorite class: " + favoriteClass);

            //log.info("Pizzas to suggest: ");

            if(pizzaToCoupon.size() >0) {
                // for (int i = 0; i < pizzaToCoupon.size(); i++) {
                //log.info(pizzaToCoupon.stream()
                //        .sorted((s1, s2) -> Double.compare(s2.getPrice(), s1.getPrice()))
                //        .collect(Collectors.toList()).get(0).getName());
                // }
                return  pizzaToCoupon.get(0);
            } else {

                List<Integer> decisionList = new ArrayList<>();
                decisionList.add(1);
                decisionList.add(2);
                decisionList.add(3);
                decisionList.add(4);

                decisionList.remove(favoriteClass-1);

                int secondFavDec = pizzaUtils.checkDistanceForNewOne(decisionList, returnUserOrders());

                List<Pizza> listOfSecondFavoriteClassPizzas = pizzaRepository.findByDecision(secondFavDec)
                        .stream().sorted((s1, s2) -> Double.compare(s2.getPrice(), s1.getPrice()))
                        .collect(Collectors.toList());

               // log.info("Pizzas to suggest: " + listOfSecondFavoriteClassPizzas.get(0).getName() + " " +
                //        listOfSecondFavoriteClassPizzas.get(0).getPrice());

                return  listOfSecondFavoriteClassPizzas.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<OrderPizza> returnUserOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findOneByEmail(auth.getName().toString());
        List<OrderPizza> people = currentUser.getOrder();
        return people;
    }

}
