'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsEntriesBatchesCtrl', function ($scope, $stateParams, ShawlsEntriesBatchesFactory, Flash,
                                                      ngTableParams) {

        var self = this;
        self.filterParams = {};


        self.reloadTable = function () {
            self.entryListTable.$params.page = 1;
            self.entryListTable.reload();
        };

        $scope.openCreatedDateFrom = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];
            $scope.datepickers[which] = true;
        };

        $scope.openCreatedDateTo = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };

        self.generateCSV = function () {
            ShawlsEntriesBatchesFactory.getReport($stateParams.inventoryId, "?" +
                (angular.isDefined(self.filterParams.createdDateFrom) ? "&createdDateFrom=" + self.filterParams.createdDateFrom.toDateString() : "") +
                (angular.isDefined(self.filterParams.createdDateTo) ? "&createdDateTo=" + self.filterParams.createdDateTo.toDateString() : "") +
                (angular.isDefined(self.filterParams.receiptNumber) ? "&receiptNumber=" + self.filterParams.receiptNumber : "")
            );
        };

        self.deleteEntry = function (entryId) {
            ShawlsEntriesBatchesFactory.deleteBatch(entryId, function () {
                self.entryListTable.reload();
                Flash.create('success', 'Entry deleted successfully', 'custom-class');

            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');

            })
        };

        self.entryListTable = new ngTableParams(
            {page: 1, count: 20},
            {
                total: 0,
                getData: function ($defer, params) {
                    var page = params.page();
                    ShawlsEntriesBatchesFactory.getBatches($stateParams.inventoryId, {
                            createdDateFrom: self.filterParams.createdDateFrom ? self.filterParams.createdDateFrom.toDateString() : undefined,
                            createdDateTo: self.filterParams.createdDateTo ? self.filterParams.createdDateTo.toDateString() : undefined,
                            receiptNumber: self.filterParams.receiptNumber,
                            sort: 'id,desc',
                            page: page - 1,
                            size: params.count()
                        },
                        function (response) {
                            self.noResults = response.data.length === 0;
                            self.entryListTable.total(response.totalElements);
                            $defer.resolve(response.data);
                        }, function (response) {
                            Flash.create('danger', response.message, 'custom-class');
                        });
                }
            }
        );
    });
