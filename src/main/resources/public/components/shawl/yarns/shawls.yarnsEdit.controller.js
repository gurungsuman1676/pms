angular.module('sbAdminApp')
    .controller('ShawlsYarnsEditCtrl', function ($stateParams, ShawlsYarnsFactory, $state, Flash) {

        var self = this;

        ShawlsYarnsFactory.getYarn($stateParams.yarnId, function (response) {
            self.yarn = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitYarn = function () {
            ShawlsYarnsFactory.updateYarn(self.yarn, function (response) {
                $state.go('dashboard.shawls.yarns.index');
                Flash.create('success', 'Yarn updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });