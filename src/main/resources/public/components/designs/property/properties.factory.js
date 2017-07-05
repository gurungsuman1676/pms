;
(function () {
    'use strict';

    var DesignsPropertiesFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getProperties = function (designId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/designs/' + designId + "/properties"
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.createProperties = function (designId,properties, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/designs/' + designId + "/properties",
                data: properties
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        return factory;
    };

    DesignsPropertiesFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('DesignsPropertiesFactory', DesignsPropertiesFactory);

}());