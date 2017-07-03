;
(function () {
    'use strict';

    var SupervisorsFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.updateLocation = function (clothId, cloth, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/clothes/' + clothId + "/locations",
                data: cloth
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };
        factory.getClothesByBarcode = function (params, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes',
                params: params
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };


        return factory;
    };

    SupervisorsFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('SupervisorsFactory', SupervisorsFactory);

}());