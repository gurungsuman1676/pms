;
(function () {
    'use strict';

    var ColorsFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getColors = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/colors'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getColor = function (colorId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/colors/' + colorId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.getCustomerColors = function(yarnId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/colors/yarns/' + yarnId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.createColor = function (color, successCallback, errorCallback) {
            if(color.customer) {
                color.customerId = color.customer.id;
            }
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/colors',
                data: color
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateColor = function (color, successCallback, errorCallback) {
            if(typeof (color.customer)!== null && typeof (color.customer) === 'object'){
                color.customerId = color.customer.id;
            }
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/colors/' + color.id,
                data: color
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    ColorsFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('ColorsFactory', ColorsFactory);

}());