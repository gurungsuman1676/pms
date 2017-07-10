'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('KnittersHistoryEditCtrl', function (KnittersHistoryFactory,
                                                     MachinesFactory, KnittersFactory, $state, $stateParams, Flash) {

        var self = this;
        self.isEdit = true;
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

        KnittersHistoryFactory.getHistory($stateParams.historyId, function (response) {
            self.history = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
        self.submitHistory = function () {
            var historyId = $stateParams.historyId;
            KnittersHistoryFactory.editHistory(historyId, self.history, function (response) {
                $state.go('dashboard.knittingHistory.index');
                Flash.create('success', ' History update successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
