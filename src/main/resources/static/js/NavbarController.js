/**
 * Created by Marcin on 30/12/2016.
 */
angular.module('nikoApp').controller('NavbarController', function ($scope, $resource, $localStorage) {
    $scope.message = 'Hello from navbarController';
    $scope.email;
    $scope.admin;
    $scope.login

    var loadCurrentUser = function () {
        //debugger;
        var User = $resource('api/user/current.json', {}, {
            query: {method: 'get', isArray: false, cancellable: true}
        });

        User.query(function (response) {
            $scope.email = response.email;
            $scope.login = response.login;
            $localStorage.email = response.email;
            $localStorage.login = response.login;
            $localStorage.role = response.role;

            if (angular.equals(response.role, 'ROLE_ADMIN')) {
                $scope.admin = true;
            } else {
                $scope.admin = false;
            }
            //alert($scope.admin);
        });
    };
    loadCurrentUser();

    $scope.removeUserFromLocalStorage = function () {
        delete $localStorage.email;
        delete $localStorage.login;
        $localStorage.$reset();
    }
});