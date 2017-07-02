;
(function () {
    'use strict';

    var ShawlsCustomersFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getCustomers = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/customers'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getCustomer = function (customerId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/customers/' + customerId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createCustomer = function (customer, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/shawls/customers',
                data: customer
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateCustomer = function (customer, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/shawls/customers/' + customer.id,
                data: customer
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    ShawlsCustomersFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('ShawlsCustomersFactory', ShawlsCustomersFactory);

}());