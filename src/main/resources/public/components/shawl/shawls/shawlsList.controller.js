'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsListCtrl', function (ShawlsFactory, Flash, ngTableParams, $filter) {

        var self = this;
        self.shawls = [];
        ShawlsFactory.getShawls(function (response) {
            self.shawls = response;
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
            self.shawlListTable.$params.page = 1;
            self.shawlListTable.reload();
        };

        self.shawlListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.shawls.length,
                getData: function ($defer, params) {
                    var orderedData = self.shawls;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });
