// create the module and name it scotchApp
var nikoApp = angular.module('nikoApp', ['ngRoute', 'ngStorage', 'ngResource', 'ngAnimate', 'ui.bootstrap', 'scrollable-table']);

nikoApp.config(function ($routeProvider, $locationProvider) {
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
        // .when('/login', {
        //     templateUrl: 'views/login2.html',
        //     controller: 'LoginController'
        // })
        .when('/menu', {
            templateUrl: 'views/pizzaAll.html',
            controller: 'PizzaAddCtrl'
        })
        .when('/register', {
            templateUrl: 'views/register.html',
            controller: 'UserController'
        })
        .when('/orderPizza', {
            templateUrl: 'views/order.html',
            controller: 'OrderController'
        })
        .when('/userSettings', {
            templateUrl: 'views/settings.html',
            controller: 'SettingsController'
        })
        .when('/userAddress', {
            templateUrl: 'views/addressManagement.html',
            controller: 'AddressController'
        })
        .otherwise({redirectTo: '/'});

    // $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    //check browser support
    // if (window.history && window.history.pushState) {
    //     //$locationProvider.html5Mode(true); will cause an error $location in HTML5 mode requires a  tag to be present! Unless you set baseUrl tag after head tag like so: <head> <base href="/">
    //
    //     // to know more about setting base URL visit: https://docs.angularjs.org/error/$location/nobase
    //
    //     // if you don't wish to set base URL then use this
    //     $locationProvider.html5Mode({
    //         enabled: true,
    //         requireBase: false
    //     });
    //
    //
    // }


});
