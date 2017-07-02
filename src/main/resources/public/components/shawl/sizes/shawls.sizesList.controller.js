'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsSizesListCtrl', function (ShawlsSizesFactory, Flash, ngTableParams, $filter) {

        var self = this;
        self.sizes = [];
        ShawlsSizesFactory.getSizes(function (response) {
            self.sizes = response;
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
            self.sizeListTable.$params.page = 1;
            self.sizeListTable.reload();
        };

        self.sizeListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.sizes.length,
                getData: function ($defer, params) {
                    var orderedData = self.sizes;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });
