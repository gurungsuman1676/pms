'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('DashboardCtrl', function ($localStorage, $scope, $position, $http) {
        $scope.isAdmin = $localStorage.isAdmin;
        $scope.isShipper = $localStorage.isShipper;
        $scope.isKnitter = $localStorage.isKnitter;
    });
