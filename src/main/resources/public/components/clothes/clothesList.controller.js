'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ClothesListCtrl', function ($window, $scope, ClothesFactory, CustomersFactory, Flash, ngTableParams, LocationsFactory, ngDialog) {

        var self = this;
        self.orderNo = ''
        self.clothes = [];
        self.showContents = true;
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

        self.statuses = [{id: 1, name: "Accepted"}, {id:2, name:"Rejected"}];

        self.types = [{ id: 0, name: "Knitting"}, {id:1, name: "Weaving"} ,{id:2 , name: "All"}];

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
            boxNumber: undefined
        }

        LocationsFactory.getLocations(function (response) {
            self.locations = addAllOptions(response);
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CustomersFactory.getCustomers(function (response) {
            self.customers = addAllOptions(response);
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        function addAllOptions(options){
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
                ngDialog.open({ template: '/components/clothes/history.html', data: history });
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
            if(request.status == 200) {
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

            self.clothTable.reload();
        }

        self.generateCSV = function () {
            ClothesFactory.getClothesReport("?" +
                 (angular.isDefined(self.filterParams.orderNo)?"&orderNo=" + self.filterParams.orderNo : "") +
                 (angular.isDefined(self.filterParams.customerId)?"&customerId="+self.filterParams.customerId : "")+
                 (angular.isDefined(self.filterParams.locationId)? "&locationId= "+self.filterParams.locationId : "")+
                 (angular.isDefined(self.filterParams.barcode)? "&barcode=" +self.filterParams.barcode : "")+
                 (angular.isDefined(self.filterParams.deliverDateFrom )? "&deliverDateFrom="+self.filterParams.deliverDateFrom.toDateString() : "")+
                 (angular.isDefined(self.filterParams.deliveryDateTo) ? "&deliveryDateTo="+self.filterParams.deliveryDateTo.toDateString() : "")+
                 (angular.isDefined(self.filterParams.orderDateFrom) ? "&orderDateFrom="+self.filterParams.orderDateFrom.toDateString() : "")+
                 (angular.isDefined(self.filterParams.orderDateTo) ? "&orderDateTo="+self.filterParams.orderDateTo.toDateString() : "")+
                 (angular.isDefined(self.filterParams.shippingNumber) ?"&shippingNumber="+self.filterParams.shippingNumber : "")+
               (angular.isDefined(self.filterParams.boxNumber)? "&boxNumber="+ self.filterParams.boxNumber: "")+
                 (angular.isDefined(self.filterParams.statusId) ? "&isReject="+ (Number(self.filterParams.statusId) === 1 ?false : true) : ""));
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
                            locationId: self.filterParams.locationId === 'All' ? undefined : self.filterParams.locationId,
                            barcode: self.filterParams.barcode,
                            deliverDateFrom: self.filterParams.deliverDateFrom ? self.filterParams.deliverDateFrom.toDateString() : undefined,
                            deliveryDateTo: self.filterParams.deliveryDateTo ? self.filterParams.deliveryDateTo.toDateString() : undefined,
                            orderDateFrom: self.filterParams.orderDateFrom ? self.filterParams.orderDateFrom.toDateString() : undefined,
                            orderDateTo: self.filterParams.orderDateTo ? self.filterParams.orderDateTo.toDateString() : undefined,
                            shippingNumber: self.filterParams.shippingNumber,
                            boxNumber: self.filterParams.boxNumber,
                            isReject: angular.isDefined(self.filterParams.statusId) ? (Number(self.filterParams.statusId) === 1 ? false : true) : null,
                            type: self.filterParams.typeId == 2 ? undefined : self.filterParams.typeId,
                            sort: 'id,desc',
                            page: page - 1,
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

    });
