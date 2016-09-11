;
(function () {
    'use strict';

    var CustomersFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getCustomers = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/customers'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getCustomer = function (customerId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/customers/' + customerId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createCustomer = function (customer, successCallback, errorCallback) {
            if(customer.parent) {
                customer.parentId = customer.parent.id;
            }
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/customers',
                data: customer
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateCustomer = function (customer, successCallback, errorCallback) {

            if(typeof (customer.parent)!== null && typeof (customer.parent) === 'object'){
                customer.parentId = customer.parent.id;
            }
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/customers/' + customer.id,
                data: customer
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.getCustomerParents = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/customers/parent'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    CustomersFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('CustomersFactory', CustomersFactory);

}());