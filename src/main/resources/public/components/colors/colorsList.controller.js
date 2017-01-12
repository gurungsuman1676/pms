'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ColorsListCtrl', function (ColorsFactory, Flash, ngTableParams, CustomersFactory, YarnsFactory,$filter) {

        var self = this;
        self.colors = [];

        CustomersFactory.getCustomers(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
        YarnsFactory.getYarns(function(response){
            self.options.yarns = response;
        },function (response) {
            Flash.create('danger', response.message, 'custom-class');

        });

        ColorsFactory.getColors(function (response) {
            self.colors = response;
            self.reloadTable();
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.reloadTable = function () {
            self.colorListTable.reload();
        };

        self.filterParams = {
            'name': '',
            yarnId: ''
        };

        var getFilterParams = function () {
            var filterParams = {};
            filterParams.name_company = self.filterParams.name;
            if (angular.isDefined(self.filterParams.yarnId) && self.filterParams.yarnId != null) {
                filterParams.yarnId = self.filterParams.yarnId;
            }
            return filterParams;
        }
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
