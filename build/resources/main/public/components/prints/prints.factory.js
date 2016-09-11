;
(function () {
    'use strict';

    var PrintsFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getPrints = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/prints'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getPrint = function (printId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/prints/' + printId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createPrint = function (print, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/prints',
                data: print
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updatePrint = function (print, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/prints/' + print.id,
                data: print
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    PrintsFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('PrintsFactory', PrintsFactory);

}());