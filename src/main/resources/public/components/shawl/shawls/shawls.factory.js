;
(function () {
    'use strict';

    var ShawlFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getShawls = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getShawl = function (shawlId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/' + shawlId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createShawl = function (shawl, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/shawls',
                data: shawl
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateShawl = function (shawl, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/shawls/' + shawl.id,
                data: shawl
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    ShawlFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('ShawlsFactory', ShawlFactory);

}());