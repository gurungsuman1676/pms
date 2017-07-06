'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('KnittersListCtrl', function (KnittersFactory, Flash,ngTableParams,$filter) {

        var self = this;
        self.knitters = [];
        KnittersFactory.getKnitters(function (response) {
            self.knitters = response;
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
            self.knitterListTable.$params.page = 1;
            self.knitterListTable.reload();
        };

        self.knitterListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.knitters.length,
                getData: function ($defer, params) {
                    var orderedData = self.knitters;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });
