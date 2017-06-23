angular.module('sbAdminApp')
    .controller('KnittersEditCtrl', function ($stateParams, KnittersFactory, $state, Flash) {

        var self = this;
        console.log("state param",$stateParams.knitterId);

        KnittersFactory.getKnitter($stateParams.knitterId, function (response) {
            self.knitter = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitKnitter = function () {
            KnittersFactory.updateKnitter(self.knitter, function (response) {
                $state.go('dashboard.knitters.index');
                Flash.create('success', 'Knitter updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });