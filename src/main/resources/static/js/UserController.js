/**
 * Created by Marcin on 24.11.2016.
 */
angular.module('nikoApp').controller('UserController', function ($scope, $resource, $http) {
    $scope.message = 'Hello from UserController';
    $scope.users;

    //$resource("../rest/api"}).get(); return an object.
    //$resource("../rest/api").query(); return an array.
//a skad bierzerz zmienna user w widoku nie rozumiem??c
                var loadAllPUserFromDb = function () {
                    var Users = $resource('user/all', {}, {
                        query: {method: 'get', isArray: true, canellable: true}
        });

        Users.query(function (response) {
            //alert(response); teraz w response masz to co bys widzial w postmanie takiego jsona
            $scope.users = response; // widoku będziesz używał teraz people
        });
    };
    loadAllPUserFromDb();

    //$scope.people = $resource('/person/all', []).get(); //to da undefined bo nie zdazyl jeszcze pobrac
    //alert($scope.people.size);
    //scope.cos = dajesz wtedy gdy chcesz miec dostep do czegos w pliku html w widoku i zbindowac na przyklad
        //[{"id":1,"login":"1","password":"1","email":"1@o2.pl","phones":0}]
    //Zapis osoby do bazy danych
    $scope.saveUser = function () {
        var login = $scope.loginOfUser; //pobieramy imie z pola w html
        var password = $scope.passwordOfUser;
        var email = $scope.emailOfUser;
        var phones = $scope.phoneOfUser;

        //alert(name); //to tylko dla testu czy dane sie pobieraja, w google chrome ctrl+shif j otwiera conosle do debuga
        //degug //tak sie wlacza debugger w js

        //Potrzebujemy stworzyc nasz obiekt, ktorego zadamy w Javie patrz RequestBody
        var userObject = {
            login: login,
            password: password,
            email: email,
            phones: phones
        };

        $http.post('/user/add',userObject).success(function () { //wywloujemy
            //alert('Thanks');
            loadAllPUserFromDb();//hmm chyba powinno dzialac
        }).error(function () {
            alert('We have problem!');
        })
    };

});