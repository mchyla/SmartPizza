/**
 * Created by Marcin on 10.01.2017.
 */
angular.module('nikoApp').service('UserService', function ($http) {
    this.editAddress = function (user) {
        return $http({
            method: "POST",
            url: 'api/user/update'/* +user.id*/,
            data: user
        }).then(function successCallback(response) {
            //return angular.toJson(response.data);
            return response;
        }, function errorCallback(response) {
            return response.status;
        });
    }

    this.addAddress = function (user) {
        return $http({
            method: "POST",
            url: 'api/user/add'/* +user.id*/,
            data: user
        }).then(function successCallback(response) {
            //return angular.toJson(response.data);
            return response;
        }, function errorCallback(response) {
            return response.status;
        });
    }

});