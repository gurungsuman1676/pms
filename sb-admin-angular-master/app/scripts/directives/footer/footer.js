'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('sbAdminApp')
    .directive('footer',function(){
        return {
            templateUrl:'scripts/directives/footer/footer.html',
            restrict: 'E',
            replace: true,
        }
    });


