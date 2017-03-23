package com.my.app.utils;

import com.my.app.model.Ingredient;
import com.my.app.model.Pizza;
import com.my.app.repository.IngredientRepository;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sun.rmi.runtime.Log;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Marcin on 16.03.2017.
 */
public class PizzaUtils {

    IngredientRepository ingredientRepository;

    public PizzaUtils(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

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
            result += Math.abs(ingredientsOfPizzaA.get(i) - ingredientsOfPizzaB.get(i)) * ingredientList.get(i).getWeight();
        }

        return result;
    }
}
