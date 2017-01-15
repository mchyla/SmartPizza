/**
 * Created by mchyl on 14/01/2017.
 */
angular.module('nikoApp').service('AddressService', function ($http) {
    this.editAddress = function (address) {
        return $http({
            method: "POST",
            url: 'api/address/update'/* +user.id*/,
            data: address
        }).then(function successCallback(response) {
            //return angular.toJson(response.data);
            return response;
        }, function errorCallback(response) {
            return response.status;
        });
    }

    this.addAddress = function (address) {
        return $http({
            method: "POST",
            url: 'api/address/add'/* +user.id*/,
            data: address
        }).then(function successCallback(response) {
            //return angular.toJson(response.data);
            return response;
        }, function errorCallback(response) {
            return response.status;
        });
    }

});