;
(function () {
    'use strict';

    var ShawlsYarnsFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getYarns = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/yarns'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getYarn = function (yarnId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/yarns/' + yarnId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createYarn = function (yarn, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/shawls/yarns',
                data: yarn
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateYarn = function (yarn, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/shawls/yarns/' + yarn.id,
                data: yarn
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    ShawlsYarnsFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('ShawlsYarnsFactory', ShawlsYarnsFactory);

}());