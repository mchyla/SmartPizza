package com.my.app.utils;

import com.my.app.model.Ingredient;
import com.my.app.model.Pizza;
import com.my.app.repository.IngredientRepository;
import com.my.app.repository.PizzaRepository;
import org.apache.log4j.Logger;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thymeleaf.expression.Lists;
import sun.rmi.runtime.Log;

import javax.persistence.criteria.CriteriaBuilder;
import java.security.Timestamp;
import java.sql.Time;
import java.util.*;

/**
 * Created by Marcin on 16.03.2017.
 */
public class PizzaUtils {

    IngredientRepository ingredientRepository;
    PizzaRepository pizzaRepository;


    public PizzaUtils(IngredientRepository ingredientRepository, PizzaRepository pizzaRepository) {
        this.ingredientRepository = ingredientRepository;
        this.pizzaRepository = pizzaRepository;
    }
    //List<Ingredient> ingredientList = ingredientRepository.findAll();

    public List<Integer> binaryzeIngredients(Pizza pizza){

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        List<String> allIngredientsNames = new ArrayList<>();

        List<String> pizzaIngredients = new ArrayList<>();

        for(Ingredient i : ingredientList){
            allIngredientsNames.add(i.getName());
        }

        for(Ingredient i : pizza.getIngredient()){
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
        for(int j = 0; j < pizzaIngredients.size(); j++) {
            for (int i = 0; i < allIngredientsNames.size(); i++) {
                if(pizzaIngredients.get(j).equals(allIngredientsNames.get(i))){
                    //binaryResult+="1";
                    binaryList.set(i, 1);
                    continue;
                }else{
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

    public static  int countOnes(String binaryResult){
        int result = 0;
        char[] tab = binaryResult.toCharArray();
        for(char znak : tab){
            if(znak=='1')
                result+=1;
        }
        return result;
    }

    public double checkDistance(List<Integer> ingredientsOfPizzaA, List<Integer> ingredientsOfPizzaB){
        double result = 0;

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        //Collections.sort(ingredientList);
        Collections.sort(ingredientList, new Comparator<Ingredient>(){
            public int compare(Ingredient s1, Ingredient s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });



        for(int i = 0; i < ingredientsOfPizzaA.size(); i++){
       /* for(char i : pizzaAIngredients){*/
            //System.out.println(ingredientsOfPizzaA.get(i) +"-"+ ingredientsOfPizzaB.get(i) +"*"+ ingredientList.get(i).getWeight());
            result += Math.abs(ingredientsOfPizzaA.get(i) - ingredientsOfPizzaB.get(i)) * ingredientList.get(i).getWeight();
        }

        return result;
    }

    public void checkDistanceFromTemplate(){
        Logger log = Logger.getLogger(PizzaUtils.class);
/*        String wiejskiGangster = "001001010000000000000100000001000100000100";
        String kowalSwinke = "001001000000000000000000100001000100000000";
        //String smieszna = "000001000000000000100000100001000110000000";
        String ryba = "000000000000100010000000000001000100000010";
        String wege = "000001000000010001000001110001000100000000"*/;

        String wiejskiGangster  = "000000000000000100000010000000000000000010";
        String kowalSwinke      = "001000000000000010001000100001000000100000";
                                                                                    //String smieszna         = "000001000000000000100000100001000110000000";
        String ryba             = "000000110000100010000000110001000100000010";
        String wege             = "000001000000010001000001110001000100000000";




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

        List<Integer>  newDec1List = new ArrayList<>(); //= charrArray2IntegerList("001001010000000000000100000001000100000100".toCharArray());
        List<Integer>  newDec2List = new ArrayList<>(); //= charrArray2IntegerList("000001000000000000100000100001000110000000".toCharArray());
        List<Integer>  newDec3List = new ArrayList<>(); //= charrArray2IntegerList("000001000000000000100000100001000110000000".toCharArray());
        List<Integer>  newDec4List = new ArrayList<>(); //= charrArray2IntegerList("000000000000100010000000000001000100000010".toCharArray());
       // List<Integer>  newDec5List = new ArrayList<>(); //= charrArray2IntegerList("000001000000010001000001110001000100000000".toCharArray());

        double distanceFromTemplate1 = 10.0;
        double distanceFromTemplate2 =  10.0;
        double distanceFromTemplate3 =  10.0;
        double distanceFromTemplate4 =  10.0;

        //1st loop of algorithm
            for (int i = 0; i < pizzas.size(); i++) {
                Pizza pizza = pizzas.get(i);
                log.info("The pizza analysis started: "+pizza.getName());
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



                log.info("Min distance from templates: "+round(listOfResults.stream().reduce(Double::min).get(),2));

                log.info("Maxdistance from templates: "+round(listOfResults.stream().reduce(Double::max).get(),2));
                log.info("Index of minimal distance: "+ listOfResults.indexOf(Collections.min(listOfResults)));
                int dec = 1 + listOfResults.indexOf(Collections.min(listOfResults));


                log.info("Classification result: "+dec);
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
        for(int x = 0; x < 11; x++) {
            for (int i = 0; i < pizzas.size(); i++) {
                Pizza pizza = pizzas.get(i);
                log.info("The pizza analysis started: "+pizza.getName());
                List<Double> listOfResults = new ArrayList<>();
                if(newDec1List.size()>0)listOfResults.add(checkDistance(binaryzeIngredients(pizza), newDec1List));
                //listOfResults.add(checkDistance(binaryzeIngredients(pizza), kowalSwinkeBinaryzedIngredientList));
                if(newDec2List.size()>0)listOfResults.add(checkDistance(binaryzeIngredients(pizza), newDec2List));
                if(newDec3List.size()>0)listOfResults.add(checkDistance(binaryzeIngredients(pizza), newDec3List));
                if(newDec4List.size()>0)listOfResults.add(checkDistance(binaryzeIngredients(pizza), newDec4List));


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

      /*  for(int i = 0 ; i < newDec1List.size(); i++){
            System.out.print(newDec1List.get(i));
        }
        System.out.println();
        for(int i = 0 ; i < newDec2List.size(); i++){
            System.out.print(newDec2List.get(i));
        }
        System.out.println();

        for(int i = 0 ; i < newDec3List.size(); i++){
            System.out.print(newDec3List.get(i));
        }
        System.out.println();

        for(int i = 0 ; i < newDec4List.size(); i++){
            System.out.print(newDec4List.get(i));
        }
            System.out.println();
            System.out.println();
            System.out.println();*/

        //return Math.min(Math.min(Math.min(Math.min(distance))))
    }
    // uzyj jako template klas decyzyjnych
    //oblicz odleglosc kazdej pizzy od kazdej z tych - zobacz wyniki
    //po wynikach ustal zakresy odleglosci do przynaleznosci
    //001001010000000000000100000001000100000100 - wiejski gangster
    //001001000000000000000000100001000100000000 - dopadl kowal swinke
    //000001000000000000100000100001000110000000 - śmiszna
    //100010000100001000000000000101000100000000 - smieszna sprawa
    //000000000000100010000000000001000100000010 - ryba
    //000001000000010001100001110001000100000000 - wege
    //000000000000000000000000000000000000000000 - INNE

    public static List<Integer> charrArray2IntegerList(char[] wiejskiGangsterCharrArray){
        List<Integer> wiejskiGangsterList = new ArrayList<>();
        for(char i : wiejskiGangsterCharrArray){
            wiejskiGangsterList.add(Character.getNumericValue(i));
        }
        return wiejskiGangsterList;
    }

    public List<Integer> binaryzeIngredientsList(List<Ingredient> listOfIngredientsToBynaryze){

        List<Ingredient> ingredientList = ingredientRepository.findAll();
        List<String> allIngredientsNames = new ArrayList<>();

        List<String> pizzaIngredients = new ArrayList<>();

        for(Ingredient i : ingredientList){
            allIngredientsNames.add(i.getName());
        }

        for(Ingredient i : listOfIngredientsToBynaryze){
            pizzaIngredients.add(i.getName());
        }

        //sort is needed after add empty elements representing lack of ingredient
        Collections.sort(allIngredientsNames);
        Collections.sort(pizzaIngredients);
        List<Integer> binaryList = new ArrayList<Integer>(Collections.nCopies(allIngredientsNames.size(), 0));
        String binaryResult = "";
        for(int j = 0; j < pizzaIngredients.size(); j++) {
            for (int i = 0; i < allIngredientsNames.size(); i++) {
                if(pizzaIngredients.get(j).equals(allIngredientsNames.get(i))){
                    //binaryResult+="1";
                    binaryList.set(i, 1);
                    continue;
                }else{
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
}
