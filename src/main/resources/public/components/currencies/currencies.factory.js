;
(function () {
    'use strict';

    var CurrenciesFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getCurrencies = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/currencies'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getCurrency = function (currencyId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/currencies/' + currencyId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createCurrency = function (currency, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/currencies',
                data: currency
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateCurrency = function (currency, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/currencies/' + currency.id,
                data: currency
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    CurrenciesFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('CurrenciesFactory', CurrenciesFactory);

}());