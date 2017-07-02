'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsSizesNewCtrl', function (ShawlsSizesFactory, $state, Flash) {

        var self = this;
        self.submitSize = function () {
            ShawlsSizesFactory.createSize(self.size, function (response) {
                $state.go('dashboard.shawls.sizes.index');
                Flash.create('success', 'New Size added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
