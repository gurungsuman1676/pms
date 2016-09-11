'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('PrintsListCtrl', function (PrintsFactory, Flash, ngTableParams,SizesFactory,$filter,CurrenciesFactory) {

        var self = this;
        self.prints = [];
        PrintsFactory.getPrints(function (response) {
            self.prints = response;

            self.reloadTable();
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.reloadTable = function () {
            self.printTableList.reload();
        };
        CurrenciesFactory.getCurrencies(function (response) {
            self.options.currenciesList = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });




        SizesFactory.getSizes(function (response) {
            self.options.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.filterParams = {
            'name': '',
            sizeId: '',
            currencyId: ''
        };

        var getFilterParams = function () {
            var filterParams = {};
            filterParams.name = self.filterParams.name;
            if (angular.isDefined(self.filterParams.sizeId) && self.filterParams.sizeId != null && self.filterParams.sizeId > 0) {
                filterParams.sizeId = self.filterParams.sizeId;
            }
            if (angular.isDefined(self.filterParams.currencyId) && self.filterParams.currencyId != null && self.filterParams.currencyId > 0) {
                filterParams.currencyId = self.filterParams.currencyId;
            }
            return filterParams;
        };

        self.printTableList = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.prints.length,
                getData: function ($defer, params) {
                    var orderedData = self.prints;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));                 }
            });


    });
