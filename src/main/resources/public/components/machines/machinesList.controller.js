'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('MachinesListCtrl', function (MachinesFactory, Flash) {

        var self = this;
        self.machines = [];
        MachinesFactory.getMachines(function (response) {
            self.machines = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
