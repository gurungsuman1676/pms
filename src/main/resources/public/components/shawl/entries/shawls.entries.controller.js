'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsEntriesCtrl', function ($scope, ShawlsEntriesFactory, Flash, ShawlsYarnsFactory,
                                               ShawlsColorsFactory, ShawlsCustomersFactory, ShawlsSizesFactory,
                                               ShawlsFactory, LocationsFactory, ngTableParams, ngDialog) {

        var self = this;
        self.filtersOptions = {};
        self.filtersOptions.yarns = [];
        self.filterParams = {};
        ShawlsYarnsFactory.getYarns(function (response) {
            self.filtersOptions.yarns = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.filtersOptions.colors = [];
        ShawlsColorsFactory.getColors(function (response) {
            self.filtersOptions.colors = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.filtersOptions.customers = [];
        ShawlsCustomersFactory.getCustomers(function (response) {
            self.filtersOptions.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.filtersOptions.sizes = [];
        ShawlsSizesFactory.getSizes(function (response) {
            self.filtersOptions.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.filtersOptions.shawls = [];
        ShawlsFactory.getShawls(function (response) {
            self.filtersOptions.shawls = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.filtersOptions.locations = [];
        LocationsFactory.getLocations({type: 'SHAWL'}, function (response) {
            self.filtersOptions.locations = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        $scope.openEntryDateFrom = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };

        $scope.openEntryDateTO = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };

        $scope.openExportDateFrom = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };

        $scope.openExportDateTO = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };

        self.reloadTable = function () {
            self.entryListTable.$params.page = 1;
            self.entryListTable.reload();
        };

        self.viewHistory = function (entryId) {
            var history;
            ShawlsEntriesFactory.getHistory(entryId, function (response) {
                history = response;
                ngDialog.open({template: '/components/clothes/history.html', data: history});
            }, function (error) {
                history = error;
            });
        };

        self.generateCSV = function () {
            ShawlsEntriesFactory.getReport("?" +
                (angular.isDefined(self.filterParams.customerId) ? "&customerId=" + self.filterParams.customerId : "") +
                (angular.isDefined(self.filterParams.locationId) ? "&locationId= " + self.filterParams.locationId : "") +
                (angular.isDefined(self.filterParams.yarnId) ? "&yarnId= " + self.filterParams.yarnId : "") +
                (angular.isDefined(self.filterParams.sizeId) ? "&sizeId= " + self.filterParams.sizeId : "") +
                (angular.isDefined(self.filterParams.shawlId) ? "&shawlId= " + self.filterParams.shawlId : "") +
                (angular.isDefined(self.filterParams.entryDateFrom) ? "&entryDateFrom=" + self.filterParams.entryDateFrom.toDateString() : "") +
                (angular.isDefined(self.filterParams.entryDateTo) ? "&entryDateTo=" + self.filterParams.entryDateTo.toDateString() : "") +
                (angular.isDefined(self.filterParams.exportDateFrom) ? "&exportDateFrom=" + self.filterParams.exportDateFrom.toDateString() : "") +
                (angular.isDefined(self.filterParams.exportDateTo) ? "&exportDateTo=" + self.filterParams.exportDateTo.toDateString() : "") +
                (angular.isDefined(self.filterParams.colorId) ? "&colorId= " + self.filterParams.colorId : ""));
        };


        self.entryListTable = new ngTableParams(
            {page: 1, count: 20},
            {
                total: 0,
                getData: function ($defer, params) {
                    var page = params.page();
                    ShawlsEntriesFactory.getEntries({
                            customerId: self.filterParams.customerId,
                            shawlId: self.filterParams.shawlId,
                            locationId: self.filterParams.locationId,
                            colorId: self.filterParams.colorId,
                            yarnId: self.filterParams.yarnId,
                            sizeId: self.filterParams.sizeId,
                            entryDateFrom: self.filterParams.entryDateFrom ? self.filterParams.entryDateFrom.toDateString() : undefined,
                            entryDateTo: self.filterParams.entryDateTo ? self.filterParams.entryDateTo.toDateString() : undefined,
                            exportDateFrom: self.filterParams.exportDateFrom ? self.filterParams.exportDateFrom.toDateString() : undefined,
                            exportDateTo: self.filterParams.exportDateTo ? self.filterParams.exportDateTo.toDateString() : undefined,
                            sort: 'lastModified,desc',
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
