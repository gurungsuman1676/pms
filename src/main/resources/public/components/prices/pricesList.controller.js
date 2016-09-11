'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('PricesListCtrl', function (PricesFactory, Flash, ngTableParams, SizesFactory, CustomersFactory, DesignsFactory, ColorsFactory, $filter,YarnsFactory) {
        var self = this;
        self.options = {};
        self.options.prices = []
        self.filterOptions = {};
        SizesFactory.getSizes(function (response) {
            self.filterOptions.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
        YarnsFactory.getYarns(function(response){
            self.filterOptions.yarns = response;
        },function (response) {
            Flash.create('danger', response.message, 'custom-class');

        });


        CustomersFactory.getCustomers(function (response) {
            self.filterOptions.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        DesignsFactory.getDesigns(function (response) {
            self.filterOptions.designs = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        ColorsFactory.getColors(function (response) {
            self.filterOptions.colors = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })


        PricesFactory.getPrices(function (response) {
            self.options.prices = response;
            self.reloadTable();
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });


        var getFilterParams = function () {
            var filterParams = {};
            filterParams.designName = self.filterParams.name;
            if (angular.isDefined(self.filterParams.customerId) && self.filterParams.customerId != null) {
                filterParams.customer = {};
                filterParams.customer.id = self.filterParams.customerId;
            }
            if (angular.isDefined(self.filterParams.designId) && self.filterParams.designId != null) {
                filterParams.designId = self.filterParams.designId;
            }
            if (angular.isDefined(self.filterParams.yarnId) && self.filterParams.yarnId != null) {
                filterParams.yarnId = self.filterParams.yarnId;
            }
            if (angular.isDefined(self.filterParams.sizeId) && self.filterParams.sizeId != null) {
                filterParams.sizeId = self.filterParams.sizeId;
            }
            return filterParams;
        }

        self.reloadTable = function () {
            self.priceTableList.reload();
        };

        self.filterParams = {
            'name': '',
            customerId: undefined,
            designId: undefined,
            yarnId: undefined,
            sizeId: undefined
        };

        self.priceTableList = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.options.prices.length,
                getData: function ($defer, params) {
                    var orderedData = self.options.prices;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });
