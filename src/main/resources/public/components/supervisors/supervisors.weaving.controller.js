'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SupervisorsWeavingCtrl', function ($scope,
                                                    $modal,
                                                    ClothesFactory,
                                                    CustomersFactory,
                                                    Flash,
                                                    ngTableParams,
                                                    LocationsFactory,
                                                    ngDialog,
                                                    DesignsFactory,
                                                    $localStorage,
                                                    PrintsFactory,
                                                    SizesFactory,
                                                    WeavingFactory) {

        var self = this;
        self.filterParams = {};

        DesignsFactory.getDesigns(function (response) {
            self.designs = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
        LocationsFactory.getLocations({type: 'WEAVING'}, function (response) {
            self.locations = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CustomersFactory.getCustomers(function (response) {
            self.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        PrintsFactory.getPrints(function (response) {
            self.prints = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');

        });
        SizesFactory.getSizes(function (response) {
            self.sizes = response
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

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

        self.reloadTable = function () {
            self.logTable.$params.page = 1;
            self.logTable.reload();
        };

        self.deleteLog = function (id) {
            WeavingFactory.deleteLog(id, function () {
                self.logTable.reload();
                Flash.create('success', 'Log deleted successfully', 'custom-class');

            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');

            })

        };


        self.generateCSV = function () {
            WeavingFactory.getReport("?" +
                (angular.isDefined(self.filterParams.orderNo) ? "&orderNo=" + self.filterParams.orderNo : "") +
                (angular.isDefined(self.filterParams.customerId) && self.filterParams.customerId != 'All' ? "&customerId=" + self.filterParams.customerId : "") +
                (angular.isDefined(self.filterParams.designId) && self.filterParams.designId != 'All' ? "&designId=" + self.filterParams.designId : "") +
                (angular.isDefined(self.filterParams.sizeId) && self.filterParams.sizeId != 'All' ? "&sizeId=" + self.filterParams.sizeId : "") +
                (angular.isDefined(self.filterParams.locationId) && self.filterParams.locationId != 'All' ? "&locationId= " + self.filterParams.locationId : "") +
                (angular.isDefined(self.filterParams.receiptNumber) ? "&receiptNumber=" + self.filterParams.receiptNumber : "") +
                (angular.isDefined(self.filterParams.createdDateFrom) ? "&createdDateFrom=" + self.filterParams.createdDateFrom.toDateString() : "") +
                (angular.isDefined(self.filterParams.createdDateTo) ? "&createdDateTo=" + self.filterParams.createdDateTo.toDateString() : "") +
                (angular.isDefined(self.filterParams.printId) && self.filterParams.printId != 'All' ? "&printId= " + self.filterParams.printId : ""));
        };

        self.getRejectedDocument = function (id, location) {
            if (location === 'REJECTED') {

                $modal.open({
                    templateUrl: '/components/supervisors/image.html',
                    size: 'lg',
                    resolve: {
                        docId: function () {
                            return id;
                        }
                    },
                    controller: ['$scope', 'WeavingFactory', 'docId', '$modalInstance', 'Flash',
                        function ($scope, WeavingFactory, designId, $modalInstance, Flash) {
                            WeavingFactory.getRejectedDocument(id, function (response) {
                                $scope.image = response;
                            }, function (response) {
                                Flash.create('danger', response.message, 'custom-class');
                            })
                        }]
                });
            }
        };
        self.logTable = new ngTableParams(
            {page: 1, count: 20},
            {
                total: 0,
                getData: function ($defer, params) {
                    var page = params.page();
                    WeavingFactory.getLogs({
                            orderNo: self.filterParams.orderNo,
                            customerId: self.filterParams.customerId === 'All' ? undefined : self.filterParams.customerId,
                            designId: self.filterParams.designId === 'All' ? undefined : self.filterParams.designId,
                            locationId: self.filterParams.locationId === 'All' ? undefined : self.filterParams.locationId,
                            receiptNumber: self.filterParams.receiptNumber,
                            createdDateFrom: self.filterParams.createdDateFrom ? self.filterParams.createdDateFrom.toDateString() : undefined,
                            createdDateTo: self.filterParams.createdDateTo ? self.filterParams.createdDateTo.toDateString() : undefined,
                            sizeId: self.filterParams.sizeId,
                            printId: self.filterParams.printId,
                            sort: 'lastModified,desc',
                            page: page - 1,
                            size: params.count()
                        },
                        function (response) {
                            self.logs = response.data;
                            if (self.logs.length == 0)
                                self.noResults = true;
                            else
                                self.noResults = false;

                            self.logTable.total(response.totalElements);

                            $defer.resolve(response.data);
                        }, function (response) {
                            Flash.create('danger', response.message, 'custom-class');
                        });
                }
            }
        );


    });
