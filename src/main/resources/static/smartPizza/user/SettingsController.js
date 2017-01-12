/**
 * Created by Marcin on 10.01.2017.
 */
angular.module('nikoApp').controller('SettingsController',
    function ($scope, $resource, $http, $localStorage, $routeParams, UserService, $rootScope) {
    $scope.message = 'Hello from settingsController';
    $scope.admin;
    $scope.login = $localStorage.login;
    $rootScope.login;

    $scope.user = {};
    //$scope.passwordOfUser = "";
    // var User
    //
    var loadCurrentUser = function () {
        //debugger;
        User = $resource('api/user/current.json', {}, {
            query: {method: 'get', isArray: false, cancellable: true}
        });

        User.query(function (response) {
           // $scope.id = response.id;
            $scope.user.login = response.login;
            $scope.user.email = response.email;
            $scope.user.phones = response.phones;
        });
    };

    loadCurrentUser();
    //
    // $scope.updateUser = function (    ) {
    //     var login = $scope.loginOfUser;
    //     alert($scope.loginOfUser);
    //     var email = $scope.emailOfUser;
    //     var phones = $scope.phoneOfUser;
    //
    //     var userObject = {
    //         login: login,
    //         email: email,
    //         phones: phones
    //     };
    //
    //     var url = 'api/user/update/' + $scope.id;
    //     $http.post(url,userObject).success(function () {
    //         $localStorage.login = login;
    //     }).error(function () {
    //         alert('We have problem!');
    //     })
    // };
    //
    $scope.updateUser = function () {
        var user = {
            id: $localStorage.id,
            login: $scope.user.login,
            email: $scope.user.email,
            phones: $scope.user.phones
        }
        UserService
            .editUser(user)
            .then(function (response) {
                if (response.status == 200) {
                    delete $localStorage.login;
                    $localStorage.login = $scope.user.login;
                    $rootScope.login = $scope.user.login;
                    loadCurrentUser();
                } else {
                    alert("Nie udało sie");
                }
            })
    }
});