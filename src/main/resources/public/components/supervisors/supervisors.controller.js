'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SupervisorsCtrl', function ($scope, SupervisorsFactory, Flash, $localStorage, ngTableParams, ClothesFactory, ngDialog, $window) {

        var self = this;
        self.cloth = {};
        self.sizes = [];
        self.reOrders = [{id: 2, name: "Re Order"}, {id: 1, name: "Bulk"}];

        self.isShippingUser = ($localStorage.user.roles).indexOf("SHIPPING") != -1;
        self.filterParams = {
            customerId: undefined,
            locationId: undefined,
            orderNo: undefined,
            deliveryDateFrom: undefined,
            deliveryDateTo: undefined,
            orderDateTo: undefined,
            gauge: undefined
        }

        // To check the delivery date
        var currentDate = new Date();
        $scope.warningDate = currentDate.setDate(currentDate.getDate() + 7);

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

        self.submitCloth = function () {
            //TODO: change clothId to cloth.id
            SupervisorsFactory.updateLocation(self.clothId, self.cloth, function () {
                self.clothId = '';
                $("#barcode").focus();
                Flash.create('success', 'Location for cloth added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        };

        self.clothTable = new ngTableParams(
            {page: 1, count: 20},
            {
                total: 0,
                getData: function ($defer, params) {
                    var page = params.page()
                    ClothesFactory.getClothesByLocation({
                            orderNo: self.filterParams.orderNo,
                            customerId: self.filterParams.customerId,
                            locationId: self.filterParams.locationId,
                            designId: self.filterParams.designId === 'All' ? undefined : self.filterParams.designId,
                            deliveryDateTo: self.filterParams.deliveryDateTo ? self.filterParams.deliveryDateTo.toDateString() : undefined,
                            gauge: self.filterParams.gauge,
                            setting: self.filterParams.setting,
                            reOrder: angular.isDefined(self.filterParams.reOrder) ? (Number(self.filterParams.reOrder) === 1 ? false : true) : null,
                            //orderNo: self.orderNo,
                            order: 'id,desc',
                            page: page - 1,

                            roles:$localStorage.user.roles,
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
