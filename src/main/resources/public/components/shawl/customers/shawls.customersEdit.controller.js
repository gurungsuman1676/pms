angular.module('sbAdminApp')
    .controller('ShawlsCustomersEditCtrl', function ($stateParams, ShawlsCustomersFactory, $state, Flash) {

        var self = this;

        ShawlsCustomersFactory.getCustomer($stateParams.customerId, function (response) {
            self.customer = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitCustomer = function () {
            ShawlsCustomersFactory.updateCustomer(self.customer, function (response) {
                $state.go('dashboard.shawls.customers.index');
                Flash.create('success', 'Customer updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });