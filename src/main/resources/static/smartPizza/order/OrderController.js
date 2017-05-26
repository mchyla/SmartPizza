/**
 * Created by Marcin on 30/12/2016.
 */
angular.module('nikoApp').controller('OrderController', function ($scope, $resource, $http, $localStorage) {
    $scope.message = 'Hello from OrderController';
    $scope.menuMessage = ''
    $scope.allInd;
    $scope.pizza;
    $scope.pizzaList = [];
    $scope.addressList = [];
    $scope.Address;
    $scope.userOrders;
    $scope.login = $localStorage.login;
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
            $scope.pizza = response;
        });
    };
    loadAllPizzaFromDb();

    var loadAllUserOrdersFromDb = function () {
        var Pizzas = $resource('api/order/all', {}, {
            query: {method: 'get', isArray: true, canellable: true}
        });
        //alert("laduje zamowienia")
        Pizzas.query(function (response) {
            $scope.userOrders = response;
        });
    };
    loadAllUserOrdersFromDb();


    var loadAllAddressFromDb = function () {
        var Address = $resource('api/address/user/all', {}, {
            query: {method: 'get', isArray: true, canellable: true}
        });

        Address.query(function (response) {
            //alert(response);
            $scope.Address = response;
        });
    };
    loadAllAddressFromDb();

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
            $scope.cost = defautCost;
            for (var i = 0; i < $scope.pizzaList.length; i++){
                $scope.cost += $scope.pizzaList[i].price;
            }
        } else {
            $scope.cost = 0;
        }
    }

    $scope.addToList = function(list, pizza){
        list.push(pizza);

        calculateCost();
    }

    $scope.removeFromList = function(list, int){
        list.splice(int, 1);

        calculateCost();
    }

    $scope.saveOrder = function () {
        var address = $scope.addressList[0];
        var pizzaList = $scope.pizzaList;
        var date = new Date();

        var userObject = {
            adress: address,
            pizzaList: pizzaList,
            price: $scope.cost,
            date: date
        };

        $http.post('/api/order/add', userObject).success(function () { //wywloujemy

            //loadAllPizzaFromDb();
        }).error(function () {
        })

    };

    $scope.deletePizza = function (pizza) {
        var id = pizza.pizzaid;
        $http.delete('/api/pizza/remove/' + id).success(function () { //wywloujemy
            //alert('Thanks');
            loadAllPizzaFromDb();
        }).error(function () {

        })
    };

});