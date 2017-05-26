/**
 * Created by mchyl on 14/01/2017.
 */
angular.module('nikoApp').controller('AddressController', function ($scope, $resource, $http, $location, AddressService, $rootScope) {
    $scope.message = 'Hello from addressController';
    $scope.Address;

    $scope.address = {};

    //$resource("../rest/api"}).get(); return an object.
    //$resource("../rest/api").query(); return an array.

    var loadAllAddressFromDb = function () {
        var Address = $resource('api/address/user/all', {}, {
            query: {method: 'get', isArray: true, canellable: true}
        });

        Address.query(function (response) {
            //alert(response);
            $scope.Address = response;
        });
    };
    loadAllAddressFromDb();

    $scope.saveAddress = function () {

        var addressObject = {
            city: $scope.address.cityOfAddress,
            street: $scope.address.routeOfAddress,
            houseNumber: $scope.address.buildingNumberOfAddress,
            flatNumber: $scope.address.flatNumberOfAddress
        };

        AddressService.addAddress(addressObject).then(function (response) {
            //alert('Thanks');
            if (response.status == 200) {
                loadAllAddressFromDb();
            } else {

            }
        });
    };


});