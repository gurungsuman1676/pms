angular.module('sbAdminApp')
    .controller('ShawlsEditCtrl', function ($stateParams, ShawlsFactory, $state, Flash) {

        var self = this;

        ShawlsFactory.getShawl($stateParams.shawlId, function (response) {
            self.shawl = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitShawl = function () {
            ShawlsFactory.updateShawl(self.shawl, function (response) {
                $state.go('dashboard.shawls.index');
                Flash.create('success', 'Shawl updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });