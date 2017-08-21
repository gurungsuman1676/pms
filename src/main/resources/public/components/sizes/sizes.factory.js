;
(function () {
    'use strict';

    var SizesFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getSizes = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/sizes'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getSizesbyYarns = function (designId, yarnId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/sizes',
                params: { designId: designId, yarnId: yarnId }
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.getSize = function (sizeId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/sizes/' + sizeId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createSize = function (size, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/sizes',
                data: size
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateSize = function (location, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/locations/' + location.id,
                data: size
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    SizesFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('SizesFactory', SizesFactory);

}());