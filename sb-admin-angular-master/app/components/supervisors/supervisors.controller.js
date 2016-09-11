'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SupervisorsCtrl', function ($scope, SupervisorsFactory, Flash, $localStorage, ngTableParams, ClothesFactory, ngDialog) {

        var self = this;
        self.cloth = {};
        self.sizes = [];
        self.isShippingUser = ($localStorage.user.roles).indexOf("SHIPPING") != -1;
        self.filterParams = {
            customerId: undefined,
            locationId: undefined,
            orderNo: undefined,
            barcode: undefined,
            deliveryDateFrom: undefined,
            deliveryDateTo: undefined,
            orderDateFrom: undefined,
            orderDateTo: undefined
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
            var url = 'http://192.168.1.113:8000/' + cloth.customer.name + '/' + cloth.price.designName + '.csv';

            Papa.parse(url, {
                download: true,
                complete: function(results, file) {
                    console.log(results);
                    ngDialog.open({ template: '/components/clothes/details.html', data: results });
                }
                // rest of config ...
            })

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
                            barcode: self.filterParams.barcode,
                            deliverDateFrom: self.filterParams.deliverDateFrom ? self.filterParams.deliverDateFrom.toDateString() : undefined,
                            deliveryDateTo: self.filterParams.deliveryDateTo ? self.filterParams.deliveryDateTo.toDateString() : undefined,
                            orderDateFrom: self.filterParams.orderDateFrom ? self.filterParams.orderDateFrom.toDateString() : undefined,
                            orderDateTo: self.filterParams.orderDateTo ? self.filterParams.orderDateTo.toDateString() : undefined,
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
