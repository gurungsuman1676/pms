angular.module('sbAdminApp')
    .controller('MachinesEditCtrl', function ($stateParams, MachinesFactory, $state, Flash) {

        var self = this;

        MachinesFactory.getMachine($stateParams.machineId, function (response) {
            self.machine = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitMachine = function () {
            MachinesFactory.updateMachine(self.machine, function (response) {
                $state.go('dashboard.machines.index');
                Flash.create('success', 'Machine updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });