;
(function () {
    'use strict';

    var WeavingFactory = function ($http, RESOURCES, $window) {
        var factory = {};

        factory.updateWeavingLocation = function (cloth, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/clothes/weaving/locations',
                data: cloth
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.uploadDocument = function (file, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/clothes/documents',
                data: file
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getRejectedDocument = function (id, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes/documents/workLogs/' + id,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.deleteLog = function (id, successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: RESOURCES.apiURL + '/clothes/weaving/logs/' + id
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getLogs = function (params, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/weaving/logs',
                params: params
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };


        factory.getReport = function (params) {
            $window.open(RESOURCES.apiURL + '/weaving/logs' + '/report' + params);
        };

        return factory;
    };

    WeavingFactory.$inject = ['$http', 'RESOURCES', '$window'];
    angular.module('sbAdminApp')
        .factory('WeavingFactory', WeavingFactory);

}());