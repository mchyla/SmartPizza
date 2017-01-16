/**
 * Created by Marcin on 30/12/2016.
 */
angular.module('nikoApp').controller('OrderController', function ($scope, $resource, $http) {
    $scope.message = 'Hello from OrderController';
    $scope.menuMessage = ''
    $scope.allInd;
    $scope.pizza;
    $scope.pizzaList = [];
    // $scope.selected;
    var defautCost = 0;
    $scope.cost = defautCost;


    //$resource("../rest/api"}).get(); return an object.
    //$resource("../rest/api").query(); return an array.

    var loadAllPizzaFromDb = function () {
        var Pizzas = $resource('api/pizza/all', {}, {
            query: {method: 'get', isArray: true, canellable: true}
        });

        Pizzas.query(function (response) {
            $scope.pizza = response; // widoku będziesz używał teraz people
        });
    };
    loadAllPizzaFromDb();

    // $scope.costOfPizza = function () {
    //     //var pizzaList = $scope.selected;
    //     $scope.cost = 0;
    //     //alert($scope.selected);
    //     if($scope.selected != null) {
    //         for (var i = 0; i < $scope.selected.length; i++) {
    //             $scope.cost += $scope.selected[i].price;
    //             //alert($scope.pizzaList[i].price);
    //         }
    //     } else {
    //         $scope.cost = 0;
    //     }
    // };

    //costOfPizza();

    var calculateCost = function(){
        if($scope.pizzaList != null){
            $scope.cost = 0;
            for (var i = 0; i < $scope.pizzaList.length; i++){
                $scope.cost += $scope.pizzaList[i].price;
            }
        } else {
            $scope.cost = 0;
        }
    }

    $scope.addToList = function(pizza){
        $scope.pizzaList.push(pizza);

        calculateCost();
    }

    $scope.removeFromList = function(int){
        $scope.pizzaList.splice(int, 1);

        calculateCost();
    }

    $scope.savePizza = function () {
        var name = $scope.pizzaName;
        var ingredientList = $scope.selectedItems;
        var price = $scope.pizzaPrice;
        alert($scope.ingredientList + " " + ingredientList)

        var userObject = {
            name: name,
            ingredient: ingredientList,
            price: price
        };

        $http.post('/api/pizza/add', userObject).success(function () { //wywloujemy
            //alert('Thanks');
            loadAllPizzaFromDb();
        }).error(function () {
            alert('We have problem!');
        })

    };

    $scope.deletePizza = function (pizza) {
        var id = pizza.pizzaid;
        alert(id);
        $http.delete('/api/pizza/remove/' + id).success(function () { //wywloujemy
            //alert('Thanks');
            loadAllPizzaFromDb();
        }).error(function () {
            alert('We have problem!');
        })
    };

});