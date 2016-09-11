'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('PricesEditCtrl', function ($scope, $stateParams, PricesFactory, CustomersFactory, ColorsFactory, SizesFactory, DesignsFactory, YarnsFactory, $state, Flash) {

        var self = this;
        self.options = {};

        PricesFactory.getPrice($stateParams.priceId, function (response) {
            self.price = response;
            self.getDesignsandYarns(self.price.customer.id);
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        SizesFactory.getSizes(function (response) {
            self.options.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });


        CustomersFactory.getCustomerParents(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        YarnsFactory.getYarns(function (response) {
            self.options.yarns = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.getDesignsandYarns = function (customerId) {
            var lookup = {};
            for (var i = 0, len = self.options.customers.length; i < len; i++) {
                lookup[self.options.customers[i].id] = self.options.customers[i];
            }
            self.price.customerCurrency = (lookup[customerId].currencyName);
            DesignsFactory.getCustomerDesigns(customerId, function (response) {
                self.options.designs = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        };

        self.submitPrice = function () {
            PricesFactory.updatePrice(self.price, function (response) {
                $state.go('dashboard.prices.index');
                Flash.create('success', 'Price updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
