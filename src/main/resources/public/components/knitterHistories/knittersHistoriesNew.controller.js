'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('KnittersHistoryNewCtrl', function (KnittersHistoryFactory, MachinesFactory, KnittersFactory, $state, $stateParams, Flash) {

        var self = this;
        self.options = {};
        self.options.machines = [];
        MachinesFactory.getMachines(function (response) {
            self.options.machines = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.options.knitters = [];
        KnittersFactory.getKnitters(function (response) {
            self.options.knitters = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
        self.submitHistory = function () {
            self.history.clothId = $stateParams.clothId;
            KnittersHistoryFactory.createKnittersHistory(self.history, function (response) {
                $state.go('dashboard.supervisors.clothes');
                Flash.create('success', 'New History added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
