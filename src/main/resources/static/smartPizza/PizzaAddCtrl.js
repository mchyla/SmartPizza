/**
 * Created by Marcin on 26/12/2016.
 */
angular.module('nikoApp').controller('PizzaAddCtrl', function ($scope, $resource, $http) {
    $scope.message = 'Hello from PizzaController';
    $scope.menuMessage = ''
    $scope.allInd;
    $scope.tempInd;
    $scope.pizza;
    $scope.selectedInd = [];

    //$resource("../rest/api"}).get(); return an object.
    //$resource("../rest/api").query(); return an array.
    var loadAllPIngredientsFromDb = function () {
        var Users = $resource('api/ingredient/all', {}, {
            query: {method: 'get', isArray: true, canellable: true}
        });

        Users.query(function (response) {
            $scope.allInd = response;
            $scope.tempInd = $scope.allInd;
        });
    };
    loadAllPIngredientsFromDb();

    $scope.addToList = function(list, item) {
        list.push(item);
    }

    $scope.removeFromList = function(list, int){
        list.splice(int, 1);
        console.log(int);
    }

    var loadAllPizzaFromDb = function () {
        var Pizzas = $resource('api/pizza/all', {}, {
            query: {method: 'get', isArray: true, canellable: true}
        });

        Pizzas.query(function (response) {
            $scope.pizza = response; // widoku będziesz używał teraz people
        });
    };
    loadAllPizzaFromDb();

    $scope.savePizza = function () {
        var name = $scope.pizzaName;
        var ingredientList = $scope.selectedInd;
        var price = $scope.pizzaPrice;
        console.log($scope.ingredientList + " " + ingredientList)

            var userObject = {
                name: name,
                ingredient: ingredientList,
                price: price,
                dec: 0
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
        $http.delete('/api/pizza/remove/' + id).success(function (response) {
            //alert('Thanks');
            loadAllPizzaFromDb();
        }).error(function (response) {
            alert("Cannot delete or update a parent row: a foreign key constraint fails!")
           // alert(response.status);
        })
    };

    $scope.loadData = function(pizza){

    }

});