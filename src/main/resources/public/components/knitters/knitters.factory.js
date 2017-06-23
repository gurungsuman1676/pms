;
(function () {
    'use strict';

    var KnitterFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getKnitters = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/knitters'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getKnitter = function (knitterId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/knitters/' + knitterId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createKnitter = function (knitter, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/knitters',
                data: knitter
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateKnitter = function (knitter, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/knitters/' + knitter.id,
                data: knitter
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    KnitterFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('KnittersFactory', KnitterFactory);

}());