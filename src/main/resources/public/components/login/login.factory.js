;
(function () {
    'use strict';

    var LoginFactory = function ($http, RESOURCES) {
        var factory = {};
        factory.login = function (user, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/authenticate',
                data: user
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        return factory;
    };

    LoginFactory.$inject = ['$http','RESOURCES'];
    angular.module('sbAdminApp')
        .factory('LoginFactory', LoginFactory);

}());