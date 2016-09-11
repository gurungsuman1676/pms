'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('YarnsNewCtrl', function ($scope, YarnsFactory, $state, Flash) {

        var self = this;

        self.submitYarn = function () {
            YarnsFactory.createYarn(self.yarn, function (response) {
                Flash.create('success', 'New Yarn added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
