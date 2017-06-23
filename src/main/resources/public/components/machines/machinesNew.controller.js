'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('MachinesNewCtrl', function (MachinesFactory, $state, Flash) {

        var self = this;
        self.submitMachine = function () {
            MachinesFactory.createMachine(self.machine, function (response) {
                $state.go('dashboard.machines.index');
                Flash.create('success', 'New Machine added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
