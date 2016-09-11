;
(function () {
    'use strict';

    var LocationsFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getLocations = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/locations'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getLocation = function (locationId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/locations/' + locationId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createLocation = function (location, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/locations',
                data: location
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateLocation = function (location, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/locations/' + location.id,
                data: location
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    LocationsFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('LocationsFactory', LocationsFactory);

}());