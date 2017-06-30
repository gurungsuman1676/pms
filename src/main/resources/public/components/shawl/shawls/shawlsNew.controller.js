'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsNewCtrl', function (ShawlsFactory, $state, Flash) {

        var self = this;
        self.submitShawl = function () {
            ShawlsFactory.createShawl(self.shawl, function (response) {
                $state.go('dashboard.shawls.index');
                Flash.create('success', 'New Shawl added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
