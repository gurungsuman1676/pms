'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('sbAdminApp')
    .directive('header', function () {
        return {
            templateUrl: 'scripts/directives/header/header.html',
            restrict: 'E',
            replace: true,
            scope: {
                'isAdmin': '=isAdmin',
                'isShipper': '=isShipper',
                'isKnitter': '=isKnitter',
                'isShawlUser': '=isShawlUser'
            }
        }
    });


