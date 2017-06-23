'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('KnittersNewCtrl', function (KnittersFactory, $state, Flash) {

        var self = this;
        self.submitKnitter = function () {
            KnittersFactory.createKnitter(self.knitter, function (response) {
                $state.go('dashboard.knitters.index');
                Flash.create('success', 'New Knitter added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
