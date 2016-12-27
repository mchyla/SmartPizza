// create the module and name it scotchApp
var nikoApp = angular.module('nikoApp', ['ngRoute', 'ngResource', 'ui.bootstrap']);

nikoApp.config(function ($routeProvider, $httpProvider) {
    $routeProvider

        .when('/', {
            templateUrl: 'views/home.html',
            controller: 'HomeController'
        })

        .when('/user', {
            templateUrl: 'views/user.html',
            controller: 'UserController'
        })

        .when('/about', {
            templateUrl: 'views/about.html',
            controller: 'AboutController'
        })
        .when('/addIngredient', {
            templateUrl: 'views/addIngredient.html',
            controller: 'IngredientController'
        })
        .when('/addPizza', {
            templateUrl: 'views/pizzaAdd.html',
            controller: 'PizzaAddCtrl'
        })
        .otherwise({redirectTo: '/'});

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});
