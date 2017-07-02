'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsColorsListCtrl', function (ShawlsColorsFactory, Flash, ngTableParams, $filter) {

        var self = this;
        self.colors = [];
        ShawlsColorsFactory.getColors(function (response) {
            self.colors = response;
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
            self.colorListTable.$params.page = 1;
            self.colorListTable.reload();
        };

        self.colorListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.colors.length,
                getData: function ($defer, params) {
                    var orderedData = self.colors;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });
