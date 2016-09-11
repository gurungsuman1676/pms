'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('PricesNewCtrl', function ($scope, PricesFactory, CustomersFactory, SizesFactory, DesignsFactory, ColorsFactory, YarnsFactory, $state, Flash) {
        var self = this;
        self.options = {};

        SizesFactory.getSizes(function (response) {
            self.options.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });


        CustomersFactory.getCustomerParents(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

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
            PricesFactory.createPrice(self.price, function (response) {
                Flash.create('success', 'New Price added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }

    });
