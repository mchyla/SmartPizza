/**
 * Created by Marcin on 26/12/2016.
 */
angular.module('nikoApp').controller('PizzaAddCtrl', function ($scope, $resource, $http) {
    $scope.message = 'Hello from PizzaController';
    $scope.menuMessage = ''
    $scope.allInd;
    $scope.pizza;
    $scope.selectedItems;

    //$resource("../rest/api"}).get(); return an object.
    //$resource("../rest/api").query(); return an array.
    var loadAllPIngredientsFromDb = function () {
        var Users = $resource('api/ingredient/all', {}, {
            query: {method: 'get', isArray: true, canellable: true}
        });

        Users.query(function (response) {
            $scope.allInd = response; // widoku będziesz używał teraz people
        });
    };
    loadAllPIngredientsFromDb();

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