'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsYarnsNewCtrl', function (ShawlsYarnsFactory, $state, Flash) {

        var self = this;
        self.submitYarn = function () {
            ShawlsYarnsFactory.createYarn(self.yarn, function (response) {
                $state.go('dashboard.shawls.yarns.index');
                Flash.create('success', 'New Yarn added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
