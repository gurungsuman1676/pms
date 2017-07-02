;
(function () {
    'use strict';

    var ShawlsColorsFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getColors = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/colors'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getColor = function (colorId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/colors/' + colorId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createColor = function (color, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/shawls/colors',
                data: color
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateColor = function (color, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/shawls/colors/' + color.id,
                data: color
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    ShawlsColorsFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('ShawlsColorsFactory', ShawlsColorsFactory);

}());