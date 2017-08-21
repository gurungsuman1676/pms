/**
 * Created by arjun on 6/22/2017.
 */
;
(function () {
    'use strict';

    var HistoryFactory = function ($http, RESOURCES, $window) {
        var factory = {};

        factory.getHistory = function (params, successCallback, errorCallback) {
            $http({
                method: 'GET',
                params: params,
                url: RESOURCES.apiURL + '/histories'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getReport = function (params) {
            $window.open(RESOURCES.apiURL + '/histories/' + '/report' + params);
        };
        return factory;
    };

    HistoryFactory.$inject = ['$http', 'RESOURCES', '$window'];
    angular.module('sbAdminApp')
        .factory('HistoryFactory', HistoryFactory);

}());