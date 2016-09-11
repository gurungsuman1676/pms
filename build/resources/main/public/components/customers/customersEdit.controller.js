'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('CustomersEditCtrl', function ($scope, $stateParams, CustomersFactory, CurrenciesFactory, $state, Flash) {

        var self = this;
        self.options = {};
        self.showCheckbox = false;
        self.disableEdit = true;
        CustomersFactory.getCustomers(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CustomersFactory.getCustomerParents(function (response) {
            self.options.customerParents = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CurrenciesFactory.getCurrencies(function (response) {
            self.options.currencies = response;
        }, function (resposne) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.showCheckbox = false;
        CustomersFactory.getCustomer($stateParams.customerId, function (response) {
            self.customer = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })


        self.submitCustomer = function () {
            CustomersFactory.updateCustomer(self.customer, function (response) {
                $state.go('dashboard.customers.index');
                Flash.create('success', 'Customer updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
