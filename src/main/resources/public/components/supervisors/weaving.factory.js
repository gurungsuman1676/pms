;
(function () {
    'use strict';

    var WeavingFactory = function ($http, RESOURCES) {
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
        return factory;
    };

    WeavingFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('WeavingFactory', WeavingFactory);

}());