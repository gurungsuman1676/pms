angular.module('sbAdminApp')
    .controller('ShawlsColorsEditCtrl', function ($stateParams, ShawlsColorsFactory, $state, Flash) {

        var self = this;

        ShawlsColorsFactory.getColor($stateParams.colorId, function (response) {
            self.color = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitColor = function () {
            ShawlsColorsFactory.updateColor(self.color, function (response) {
                $state.go('dashboard.inventory.colors.index');
                Flash.create('success', 'Color updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });