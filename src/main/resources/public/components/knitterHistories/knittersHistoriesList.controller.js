'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('KnittersHistoryListCtrl', function (KnittersHistoryFactory, ngTableParams, MachinesFactory, KnittersFactory, Flash, $scope) {

        var self = this;
        self.filterOptions = {};
        self.filterParams = {};

        self.filterOptions.machines = [];
        MachinesFactory.getMachines(function (response) {
            self.filterOptions.machines = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.filterOptions.knitters = [];
        KnittersFactory.getKnitters(function (response) {
            self.filterOptions.knitters = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.reloadTable = function () {
            self.historyTable.$params.page = 1;
            self.historyTable.reload();
        }

        $scope.openCompleted = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];
            $scope.datepickers[which] = true;
        };

        $scope.openDateFrom = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];
            $scope.datepickers[which] = true;
        };

        $scope.openDateTo = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };
        self.generateCSV = function () {
            KnittersHistoryFactory.getReport(
                "?" +
                (angular.isDefined(self.filterParams.knitterId) && self.filterParams.knitterId != 'All' ? "&knitterId=" + self.filterParams.knitterId : "") +
                (angular.isDefined(self.filterParams.machineId) && self.filterParams.machineId != 'All' ? "&machineId=" + self.filterParams.machineId : "") +
                (angular.isDefined(self.filterParams.completedDate) ? "&completedDate=" + self.filterParams.completedDate.toDateString() : "") +
                (angular.isDefined(self.filterParams.dateFrom) ? "&dateFrom=" + self.filterParams.dateFrom.toDateString() : "") +
                (angular.isDefined(self.filterParams.dateTo) ? "&dateTo=" + self.filterParams.dateTo.toDateString() : "")
            );

        };
        self.deleteHistory = function (id) {
            KnittersHistoryFactory.deleteHistory(id,function (response) {
                self.historyTable.reload();
                Flash.create('success', "History deleted successfully", 'custom-class');
            },function (response) {
                Flash.create('danger', response.message, 'custom-class');

            })
        };

        self.historyTable = new ngTableParams(
            {page: 1, count: 20},
            {
                total: 0,
                getData: function ($defer, params) {
                    var page = params.page();
                    KnittersHistoryFactory.getKnittersHistory({
                            knitterId: self.filterParams.knitterId === 'All' ? undefined : self.filterParams.knitterId,
                            machineId: self.filterParams.machineId === 'All' ? undefined : self.filterParams.machineId,
                            completedDate: self.filterParams.completedDate ? self.filterParams.completedDate.toDateString() : undefined,
                            dateFrom: self.filterParams.dateFrom ? self.filterParams.dateFrom.toDateString() : undefined,
                            dateTo: self.filterParams.dateTo ? self.filterParams.dateTo.toDateString() : undefined,
                            sort: 'id,desc',
                            page: page - 1,
                            size: params.count()
                        },
                        function (response) {
                            self.historyTable.total(response.totalElements);
                            if (response.data.length === 0) {
                                self.noResults = true;
                            }
                            else {
                                self.noResults = false;
                            }
                            $defer.resolve(response.data);
                        }, function (response) {
                            Flash.create('danger', response.message, 'custom-class');
                        });
                }
            }
        );

    });
