;
(function () {
    'use strict';

    var ShawlsEntriesBatchesFactory = function ($http, RESOURCES, $window) {
        var factory = {};

        factory.getBatches = function (entryId, params, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/shawls/entries/' + entryId + '/batches',
                params: params
            }).success(function (response) {
                successCallback(response);
            }).error(function (error) {
                errorCallback(error);
            })
        };

        factory.getReport = function (id, params) {
            $window.open(RESOURCES.apiURL + '/shawls/entries/' + id + '/batches/report' + params);
        };

        return factory;
    };

    ShawlsEntriesBatchesFactory.$inject = ['$http', 'RESOURCES', '$window'];
    angular.module('sbAdminApp')
        .factory('ShawlsEntriesBatchesFactory', ShawlsEntriesBatchesFactory);

}());