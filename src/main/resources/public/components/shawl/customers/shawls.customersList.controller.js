'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsCustomersListCtrl', function (ShawlsCustomersFactory, Flash, ngTableParams, $filter) {

        var self = this;
        self.customers = [];
        ShawlsCustomersFactory.getCustomers(function (response) {
            self.customers = response;
            self.reloadTable();
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        var getFilterParams = function () {
            var filterParams = {};
            filterParams.name = self.filterParams.name;
            return filterParams;
        };

        self.filterParams = {
            name: ''
        };

        self.reloadTable = function () {
            self.customerListTable.$params.page = 1;
            self.customerListTable.reload();
        };

        self.customerListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.customers.length,
                getData: function ($defer, params) {
                    var orderedData = self.customers;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });
