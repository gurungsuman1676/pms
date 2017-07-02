;
(function () {
    'use strict';

    var ShawlsSizesFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getSizes = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/sizes'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getSize = function (sizeId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/sizes/' + sizeId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createSize = function (size, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/shawls/sizes',
                data: size
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateSize = function (size, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/shawls/sizes/' + size.id,
                data: size
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    ShawlsSizesFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('ShawlsSizesFactory', ShawlsSizesFactory);

}());