'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('CustomersNewCtrl', function ($scope, CustomersFactory, CurrenciesFactory, $state, Flash) {

        var self = this;
        self.showCheckbox = true;
        self.options = {};

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

        self.submitCustomer = function () {
            if (!self.customer.parentName) {
                self.customer.parentId = null;
            }
            CustomersFactory.createCustomer(self.customer, function (response) {
                Flash.create('success', 'New Customer added successfully', 'custom-class');
                self.options.customerParents.push(response);
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    }
)
;
