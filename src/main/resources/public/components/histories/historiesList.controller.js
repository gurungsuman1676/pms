'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('HistoriesListCtrl', function (HistoryFactory, ngTableParams, Flash, $scope,$localStorage) {

        var self = this;
        self.filterOptions = {};
        self.filterParams = {};
        self.showContents = true;
        self.filterOptions.machines = [];

        self.reloadTable = function () {
            self.historyTable.$params.page = 1;
            self.historyTable.reload();
        };

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
            HistoryFactory.getReport(
                "?" +
                (angular.isDefined(self.filterParams.orderNo) && self.filterParams.orderNo != 'All' ? "&orderNo=" + self.filterParams.orderNo : "") +
                (angular.isDefined(self.filterParams.completedDate) ? "&completedDate=" + self.filterParams.completedDate.toDateString() : "") +
                (angular.isDefined(self.filterParams.dateFrom) ? "&dateFrom=" + self.filterParams.dateFrom.toDateString() : "") +
                (angular.isDefined(self.filterParams.dateTo) ? "&dateTo=" + self.filterParams.dateTo.toDateString() : "")
                +"&roles=" + $localStorage.user.roles
            );

        };

        self.historyTable = new ngTableParams(
            {page: 1, count: 20},
            {
                total: 0,
                getData: function ($defer, params) {
                    var page = params.page();
                    HistoryFactory.getHistory({
                            orderNo: self.filterParams.orderNo,
                            completedDate: self.filterParams.completedDate ? self.filterParams.completedDate.toDateString() : undefined,
                            dateFrom: self.filterParams.dateFrom ? self.filterParams.dateFrom.toDateString() : undefined,
                            dateTo: self.filterParams.dateTo ? self.filterParams.dateTo.toDateString() : undefined,
                            sort: 'id,desc',
                            page: page - 1,
                            roles: $localStorage.user.roles,
                            size: params.count()
                        },
                        function (response) {
                            self.historyTable.total(response.totalElements);
                            self.histories = response.data;
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
