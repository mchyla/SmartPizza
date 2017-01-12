/**
 * Created by Marcin on 26/12/2016.
 */
angular.module('nikoApp').controller('IngredientController', function ($scope, $resource, $http) {
    $scope.message = 'Hello from InredientController';
    $scope.ingredients;

    //$resource("../rest/api"}).get(); return an object.
    //$resource("../rest/api").query(); return an array.
    var loadAllPIngredientsFromDb = function () {
        var Users = $resource('api/ingredient/all', {}, {
            query: {method: 'get', isArray: true, canellable: true}
        });

        Users.query(function (response) {
            $scope.ingredients = response; // widoku będziesz używał teraz people
        });
    };
    loadAllPIngredientsFromDb();

    //$scope.people = $resource('/person/all', []).get(); //to da undefined bo nie zdazyl jeszcze pobrac
    //scope.cos = dajesz wtedy gdy chcesz mie dostep do czegos w pliku html w widoku i zbindowac na przyklad
    //[{"id":1,"login":"1","password":"1","email":"1@o2.pl","phones":0}]
    //Zapis osoby do bazy danych
    $scope.saveIngredient = function () {
        var name = $scope.ingredientName;
        //alert($scope.ingredientName);
        var vege = $scope.ingredientVege;
//to patrz jak nawet końska spierolina mi sięwyjebuje
        var userObject = {
            name: name,
            vege: vege
        };

        $http.post('/api/ingredient/add',userObject).success(function () { //wywloujemy
            //alert('Thanks');
            loadAllPIngredientsFromDb();
        }).error(function () {
            alert('We have problem!');
        })
    };

});