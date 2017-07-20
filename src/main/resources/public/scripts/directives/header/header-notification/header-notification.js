'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('sbAdminApp')
    .directive('headerNotification', function () {
        return {
            templateUrl: 'scripts/directives/header/header-notification/header-notification.html',
            restrict: 'E',
            replace: true,
            controller: function ($localStorage, $scope, $state) {
                $scope.logout = function () {
                    delete $localStorage.user;
                    $state.go('login');

                };

                $scope.getUserName = function () {
                    if (angular.isDefined($localStorage.user)) {
                        return $localStorage.user.username;
                    } else {
                        return "N/A";
                    }
                }


            },
        }
    });


