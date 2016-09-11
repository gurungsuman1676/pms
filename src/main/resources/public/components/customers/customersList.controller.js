'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('CustomersListCtrl', function (CustomersFactory, Flash,ngTableParams,$filter) {

        var self = this;
        self.customers = [];


        self.reloadTable = function () {
            self.customerListTable.reload();
        };

        self.filterParams = {
            'name': '',
            parentId: ''
        };

        var getFilterParams = function () {
            var filterParams = {};
            filterParams.name = self.filterParams.name;
            if (angular.isDefined(self.filterParams.parentId) && self.filterParams.parentId != null && self.filterParams.parentId > 0) {
                filterParams.parentId = self.filterParams.parentId;
            }
            return filterParams;
        }


        CustomersFactory.getCustomers(function (response) {
            self.customers = response;
            self.reloadTable();

        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CustomersFactory.getCustomerParents(function (response) {
            self.options.customerParents = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });


        self.customerListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.customers.length,
                getData: function ($defer, params) {
                    var orderedData = self.customers;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));                }
            });
    });
