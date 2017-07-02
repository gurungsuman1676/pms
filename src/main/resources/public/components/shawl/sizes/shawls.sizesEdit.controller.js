angular.module('sbAdminApp')
    .controller('ShawlsSizesEditCtrl', function ($stateParams, ShawlsSizesFactory, $state, Flash) {

        var self = this;

        ShawlsSizesFactory.getSize($stateParams.sizeId, function (response) {
            self.size = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitSize = function () {
            ShawlsSizesFactory.updateSize(self.size, function (response) {
                $state.go('dashboard.shawls.sizes.index');
                Flash.create('success', 'Size updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });