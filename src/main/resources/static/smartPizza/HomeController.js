angular.module('nikoApp').controller('HomeController', function ($scope, $localStorage) {
    $scope.message = 'Hello from HomeController';
    $scope.login;
    $scope.login = $localStorage.login;


});