'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsColorsNewCtrl', function (ShawlsColorsFactory, $state, Flash) {

        var self = this;
        self.submitColor = function () {
            ShawlsColorsFactory.createColor(self.color, function (response) {
                $state.go('dashboard.shawls.colors.index');
                Flash.create('success', 'New Color added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
