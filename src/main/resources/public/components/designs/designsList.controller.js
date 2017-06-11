'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('DesignsListCtrl', function ($scope, DesignsFactory,ngTableParams,CustomersFactory,$filter) {

        var self = this;
        self.designs = [];
        self.options ={};

        self.reloadTable = function () {
            self.designListTable.$params.page = 1;
            self.designListTable.reload();
        };

        self.filterParams = {
            'name': '',
            customerId: ''
        };

        CustomersFactory.getCustomers(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        var getFilterParams = function () {
            var filterParams = {};
            filterParams.name = self.filterParams.name;
            if (angular.isDefined(self.filterParams.customerId) && self.filterParams.customerId != null) {
                filterParams.customerId = self.filterParams.customerId;
            }
            return filterParams;
        }

        DesignsFactory.getDesigns(function (response) {
            self.designs = response;
            self.reloadTable();
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.designListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.designs.length,
                getData: function ($defer, params) {
                    var orderedData = self.designs;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });