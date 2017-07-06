'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('MachinesListCtrl', function (MachinesFactory, Flash,ngTableParams,$filter) {

        var self = this;
        self.machines = [];
        MachinesFactory.getMachines(function (response) {
            self.machines = response;
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
            self.machineListTable.$params.page = 1;
            self.machineListTable.reload();
        };

        self.machineListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.machines.length,
                getData: function ($defer, params) {
                    var orderedData = self.machines;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });
