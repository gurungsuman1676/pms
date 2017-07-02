'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsCustomersNewCtrl', function (ShawlsCustomersFactory, $state, Flash) {

        var self = this;
        self.submitCustomer = function () {
            ShawlsCustomersFactory.createCustomer(self.customer, function (response) {
                $state.go('dashboard.shawls.customers.index');
                Flash.create('success', 'New Customer added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
