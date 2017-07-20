'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('YarnsListCtrl', function (YarnsFactory, Flash,ngTableParams,$filter) {

        var self = this;
        self.yarns = [];
        YarnsFactory.getYarns(function (response) {
            self.yarns = response;
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
            self.yarnListTable.$params.page = 1;
            self.yarnListTable.reload();
        };

        self.yarnListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.yarns.length,
                getData: function ($defer, params) {
                    var orderedData = self.yarns;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });
