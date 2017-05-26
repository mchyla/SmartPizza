/**
 * Created by Marcin on 24.11.2016.
 */
angular.module('nikoApp').controller('UserController', function ($scope, $resource, $http, $location, UserService) {
        $scope.message = 'Hello from UserController';
        $scope.users;

        //$resource("../rest/api"}).get(); return an object.
        //$resource("../rest/api").query(); return an array.

        var loadAllPUserFromDb = function () {
            var Users = $resource('api/user/all', {}, {
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

            var userObject = {
                login: $scope.loginOfUser,
                password: $scope.passwordOfUser,
                email: $scope.emailOfUser,
                phones: $scope.phonesOfUser
            };

            UserService.addAddress(userObject).then(function (response) { //wywloujemy
                //alert('Thanks');
                if (response.status == 200) {
                    loadAllPUserFromDb();//hmm chyba powinno dzialac
                } else {
                    console.log('We have problem!');
                }
            });
        };

        $scope.registerUser = function () {
            var userObject = {
                login: $scope.loginOfUser,
                password: $scope.passwordOfUser,
                email: $scope.emailOfUser,
                phones: $scope.phonesOfUser
            };

            UserService.addAddress(userObject).then(function (response) {
                //alert('Thanks');
                if (response.status == 200) {
                    console.log('Thanks');
                    $location.path('/home');
                } else {
                    console.log('We have problem!');
                }
            });


        }
    }
);