/**
 * Created by arjun on 6/22/2017.
 */
;
(function () {
    'use strict';

    var KnittersHistoryFactory = function ($http, RESOURCES, $window) {
        var factory = {};

        factory.getKnittersHistory = function (params, successCallback, errorCallback) {
            $http({
                method: 'GET',
                params: params,
                url: RESOURCES.apiURL + '/knitters-history'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };


        factory.getHistory = function (id, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/knitters-history/' + id
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.createKnittersHistory = function (knittersHistory, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/knitters-history',
                data: knittersHistory
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.editHistory = function (historyId, knittersHistory, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/knitters-history/' + historyId,
                data: knittersHistory
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.deleteHistory = function (id, successCallback, errorCallback) {
            $http({
                method: 'DELETE',
                url: RESOURCES.apiURL + '/knitters-history/' + id,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };
        factory.getReport = function (params) {
            $window.open(RESOURCES.apiURL + '/knitters-history' + '/report' + params);
        };
        return factory;
    };

    KnittersHistoryFactory.$inject = ['$http', 'RESOURCES', '$window'];
    angular.module('sbAdminApp')
        .factory('KnittersHistoryFactory', KnittersHistoryFactory);

}());