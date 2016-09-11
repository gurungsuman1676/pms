'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SizesEditCtrl', function ($stateParams, SizesFactory, $state, Flash) {

        var self = this;

        SizesFactory.getSize($stateParams.sizeId, function (response) {
            self.size = response;
        }, function (response) {
            // TODO error handling;
            Flash.create('danger', response.message, 'custom-class');
        })

        self.submitSize = function () {
            SizesFactory.updateSize(self.size, function (response) {
                $state.go('dashboard.sizes.index');
                Flash.create('success', 'Size updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
