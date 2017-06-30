'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ClothesListCtrl', function ($window, $scope, $state, ClothesFactory, CustomersFactory, Flash, ngTableParams, LocationsFactory, ngDialog, DesignsFactory, $localStorage, ColorsFactory) {

        var self = this;
        self.orderNo = ''
        self.clothes = [];
        self.showContents = true;

        $scope.selectedType = 'Order No';

        $scope.typeSelected = function (id) {
            if (id == 1) {
                $scope.$apply(function () {
                    $scope.selectedType = 'Invoice No';
                });
            }
            else {
                $scope.$apply(function () {
                    $scope.selectedType = 'Order No';
                });
            }
        }


        ColorsFactory.getColors(function (response) {
            self.colors = [];
            for (var i = 0, len = response.length; i < len; i++) {
                self.colors.push({
                    id: response[i].id,
                    name: response[i].code
                })
            }
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        self.allSelected = function () {
            if (self.selectAll == true) {
                angular.forEach(self.clothes, function (value) {
                    value.isChecked = true;
                })
            }
            else {
                angular.forEach(self.clothes, function (value) {
                    value.isChecked = false;
                })
            }
        };

        self.statuses = [{id: 1, name: "Accepted"}, {id: 2, name: "Rejected"}];
        self.reOrders = [{id: 2, name: "Re Order"}, {id: 1, name: "Bulk"}];


        self.types = [{id: 0, name: "Knitting"}, {id: 1, name: "Weaving"}, {id: 2, name: "All"}];

        self.downloadOrderSheet = function () {
            ClothesFactory.downloadOrderSheet(self.filterParams.orderNo, self.filterParams.customerId);
        };

        self.downloadShippingList = function () {
            ClothesFactory.downloadShippingList(self.filterParams.shippingNumber);
        };

        self.downloadPendingList = function () {
            ClothesFactory.downloadPendingList(self.filterParams.orderNo, self.filterParams.customerId);
        };

        self.downloadInvoice = function () {
            ClothesFactory.downloadInvoice(self.filterParams.customerId, self.filterParams.shippingNumber);
        };

        self.downloadProformaInvoice = function () {
            ClothesFactory.downloadProformaInvoice(self.filterParams.orderNo, self.filterParams.customerId);
        };

        self.filterParams = {
            customerId: undefined,
            locationId: undefined,
            orderNo: undefined,
            barcode: undefined,
            deliveryDateFrom: undefined,
            deliveryDateTo: undefined,
            orderDateFrom: undefined,
            orderDateTo: undefined,
            shippingNumber: undefined,
            boxNumber: undefined,
            designId: undefined,
            gauge: undefined
        };

        DesignsFactory.getDesigns(function (response) {
            self.designs = addAllOptions(response)
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
        LocationsFactory.getLocations({type: 'KNITTING'},function (response) {
            self.locations = addAllOptions(response);
            self.locations.unshift({name: 'Empty', id: '-1'});

        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CustomersFactory.getCustomers(function (response) {
            self.customers = addAllOptions(response);
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        function addAllOptions(options) {
            options.unshift({name: 'All', id: 'All'});
            return options;
        }

        self.generateBarcode = function () {
            var noClothSelected = false;
            angular.forEach(self.clothes, function (value) {
                if (value.isChecked == true) {
                    noClothSelected = true;
                }
            })
            if (!noClothSelected)
                Flash.create('danger', 'No Clothes Selected', 'custom-class');
            else {
                self.showBarcode = true;
                self.showContents = false;
            }
        }

        self.deleteCloth = function (cloth) {
            if (cloth.locationName == "N/A") {
                var r = confirm("Are you sure you want to delte the cloth?");
                if (r == true) {
                    ClothesFactory.deleteCloth(cloth, function (response) {
                        console.log(cloth);
                        Flash.create('success', 'Cloth deleted successfully', 'custom-class');
                        self.reloadTable();
                    }, function (response) {
                        Flash.create('danger', response.message, 'custom-class');
                    });
                }
            }
            else
                Flash.create('danger', 'You can not delete the cloth from ' + cloth.locationName, 'custom-class');
        }

        self.viewHistory = function (cloth) {
            var history;
            ClothesFactory.getHistory(cloth, function (response) {
                console.log(response);
                history = response;
                ngDialog.open({template: '/components/clothes/history.html', data: history});
            }, function (error) {
                history = error;
            });
        }

        self.generateWeavingInvoice = function () {
            ClothesFactory.getWeavingReport(self.filterParams.orderNo);
        }

        self.viewDetails = function (cloth) {
            var url1 = 'http://localhost/customers/' + cloth.customer.name + '/' + cloth.price.designName + '.xls';
            var url2 = 'http://localhost/customers/' + cloth.customer.name + '/' + cloth.price.designName + '.xlsx';

            var request = new XMLHttpRequest();
            request.open('HEAD', url1, false);
            request.send();
            if (request.status == 200) {
                $window.open(url1);
            } else {
                $window.open(url2);
            }
            // Papa.parse(url, {
            //     download: true,
            //     complete: function(results, file) {
            //         console.log(results);
            //         ngDialog.open({ template: '/components/clothes/details.html', data: results });
            //     }
            //     // rest of config ...
            // })

        }

        self.hideBarcode = function () {
            self.showBarcode = false;
            self.showContents = true;

        }

        self.reloadTable = function () {

            if (self.filterParams.typeId == 1) {
                self.weavingSelected = true;
            }
            else {
                self.weavingSelected = false;
            }
            self.clothTable.$params.page = 1;
            self.clothTable.reload();
        }

        self.generateCSV = function () {
            ClothesFactory.getClothesReport("?" +
                (angular.isDefined(self.filterParams.orderNo) ? "&orderNo=" + self.filterParams.orderNo : "") +
                (angular.isDefined(self.filterParams.customerId) && self.filterParams.customerId != 'All' ? "&customerId=" + self.filterParams.customerId : "") +
                (angular.isDefined(self.filterParams.designId) && self.filterParams.designId != 'All' ? "&customerId=" + self.filterParams.designId : "") +
                (angular.isDefined(self.filterParams.locationId) && self.filterParams.locationId != 'All' ? "&locationId= " + self.filterParams.locationId : "") +
                (angular.isDefined(self.filterParams.barcode) ? "&barcode=" + self.filterParams.barcode : "") +
                (angular.isDefined(self.filterParams.deliverDateFrom) ? "&deliverDateFrom=" + self.filterParams.deliverDateFrom.toDateString() : "") +
                (angular.isDefined(self.filterParams.deliveryDateTo) ? "&deliveryDateTo=" + self.filterParams.deliveryDateTo.toDateString() : "") +
                (angular.isDefined(self.filterParams.orderDateFrom) ? "&orderDateFrom=" + self.filterParams.orderDateFrom.toDateString() : "") +
                (angular.isDefined(self.filterParams.orderDateTo) ? "&orderDateTo=" + self.filterParams.orderDateTo.toDateString() : "") +
                (angular.isDefined(self.filterParams.shippingNumber) ? "&shippingNumber=" + self.filterParams.shippingNumber : "") +
                (angular.isDefined(self.filterParams.boxNumber) ? "&boxNumber=" + self.filterParams.boxNumber : "") +
                (angular.isDefined(self.filterParams.locationDate) ? "&locationDate=" + self.filterParams.locationDate.toDateString() : "") +
                (angular.isDefined(self.filterParams.typeId) && self.filterParams.typeId != 'All' && Number(self.filterParams.typeId) !== 2 ? "&type=" + self.filterParams.typeId : "") +
                (angular.isDefined(self.filterParams.statusId) ? "&isReject=" + (Number(self.filterParams.statusId) === 1 ? false : true) : "") +
                (angular.isDefined(self.filterParams.colorId) && self.filterParams.colorId != 'All' ? "&colorId= " + self.filterParams.colorId : "") +
                (angular.isDefined(self.filterParams.gauge) ? "&gauge=" + self.filterParams.gauge : "") +
                (angular.isDefined(self.filterParams.setting) ? "&setting=" + self.filterParams.setting : "") +
                (angular.isDefined(self.filterParams.week) ? "&week=" + self.filterParams.week : "") +
                (angular.isDefined(self.filterParams.statusId) ? "&reOrder=" + (Number(self.filterParams.reOrder) === 1 ? false : true) : "") +

                "&roles=" + $localStorage.user.roles);
        }

        self.minimum_date = new Date();

        // To check the delivery date
        var currentDate = new Date();
        $scope.warningDate = currentDate.setDate(currentDate.getDate() + 7);

        //opens date picker
        $scope.openOrderDateFrom = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];
            $scope.datepickers[which] = true;
        };

        $scope.openOrderDateTo = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };

        $scope.openDeliveryDateFrom = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };

        $scope.openDeliveryDateTO = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers = [];

            $scope.datepickers[which] = true;
        };

        $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd/MM/yyyy', 'shortDate'];
        $scope.format = $scope.formats[2];


        self.clothTable = new ngTableParams(
            {page: 1, count: 20},
            {
                total: 0,
                getData: function ($defer, params) {
                    var page = params.page();
                    ClothesFactory.getClothes({
                            orderNo: self.filterParams.orderNo,
                            customerId: self.filterParams.customerId === 'All' ? undefined : self.filterParams.customerId,
                            designId: self.filterParams.designId === 'All' ? undefined : self.filterParams.designId,
                            locationId: self.filterParams.locationId === 'All' ? undefined : self.filterParams.locationId,
                            barcode: self.filterParams.barcode,
                            deliverDateFrom: self.filterParams.deliverDateFrom ? self.filterParams.deliverDateFrom.toDateString() : undefined,
                            deliveryDateTo: self.filterParams.deliveryDateTo ? self.filterParams.deliveryDateTo.toDateString() : undefined,
                            orderDateFrom: self.filterParams.orderDateFrom ? self.filterParams.orderDateFrom.toDateString() : undefined,
                            orderDateTo: self.filterParams.orderDateTo ? self.filterParams.orderDateTo.toDateString() : undefined,
                            shippingNumber: self.filterParams.shippingNumber,
                            boxNumber: self.filterParams.boxNumber,
                            isReject: angular.isDefined(self.filterParams.statusId) ? (Number(self.filterParams.statusId) === 1 ? false : true) : null,
                            type: angular.isDefined(self.filterParams.typeId) && self.filterParams.typeId != 'All' && Number(self.filterParams.typeId) != 2 ? Number(self.filterParams.typeId) : null,
                            locationDate: self.filterParams.locationDate ? self.filterParams.locationDate.toDateString() : undefined,
                            gauge: self.filterParams.gauge,
                            setting: self.filterParams.setting,
                            reOrder: angular.isDefined(self.filterParams.reOrder) ? (Number(self.filterParams.reOrder) === 1 ? false : true) : null,
                            week: self.filterParams.week,
                            colorId: self.filterParams.colorId,
                            sort: 'lastModified,desc',
                            page: page - 1,
                            roles: $localStorage.user.roles,
                            size: params.count()
                        },
                        function (response) {
                            self.clothes = response.data;
                            if (self.clothes.length == 0)
                                self.noResults = true;
                            else
                                self.noResults = false;

                            self.clothTable.total(response.totalElements);

                            $defer.resolve(response.data);
                        }, function (response) {
                            Flash.create('danger', response.message, 'custom-class');
                        });
                }
            }
        );
        self.isKnitter = function () {
            return angular.isDefined($localStorage.user) && ($localStorage.user.roles).indexOf("PRE-KNITTING") != -1;
        };

        self.onClothClicked = function (clothId) {
            if (self.isKnitter()) {
                $state.go("dashboard.knittingHistory.new", {"clothId": clothId});
            }

        };
        self.importFromExcel = function (file, type) {
            if (file) {
                console.log(file);
                ClothesFactory.uploadExcel(file, type, function (success) {
                        Flash.create('success', 'Cloth added successfully', 'custom-class');
                        self.reloadTable();
                    }, function (error) {
                        Flash.create('danger', error.message, 'custom-class');
                    }
                )

            }

        }

    });
