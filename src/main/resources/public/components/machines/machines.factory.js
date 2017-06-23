;
(function () {
    'use strict';

    var MachineFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getMachines = function (successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/machines'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        };

        factory.getMachine = function (machineId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/machines/' + machineId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.createMachine = function (machine, successCallback, errorCallback) {
            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/machines',
                data: machine
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateMachine = function (machine, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/machines/' + machine.id,
                data: machine
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }
        return factory;
    };

    MachineFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('MachinesFactory', MachineFactory);

}());