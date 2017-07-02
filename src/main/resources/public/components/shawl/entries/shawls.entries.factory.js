;
(function () {
    'use strict';

    var ShawlsEntriesFactory = function ($http, RESOURCES,$window) {
        var factory = {};

        factory.addEntry = function (entry, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/shawls/entries',
                data: entry
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getEntries = function (params, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/entries',
                params: params
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };
        factory.getHistory = function (entryId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/entries/' + entryId + '/activities'
            }).success(function (response) {
                successCallback(response);
            }).error(function (error) {
                errorCallback(error);
            })
        };

        factory.getReport = function (params) {
            $window.open(RESOURCES.apiURL + '/shawls/entries/report' + params);
        };

        return factory;
    };

    ShawlsEntriesFactory.$inject = ['$http', 'RESOURCES','$window'];
    angular.module('sbAdminApp')
        .factory('ShawlsEntriesFactory', ShawlsEntriesFactory);

}());