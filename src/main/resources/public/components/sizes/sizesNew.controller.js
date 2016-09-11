'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SizesNewCtrl', function (SizesFactory, $state, Flash) {

        var self = this;
        self.submitSize = function () {
            SizesFactory.createSize(self.size, function (response) {
                Flash.create('success', 'New Size added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
