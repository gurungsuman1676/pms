'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ClothesNewCtrl', function ($scope, ClothesFactory, CustomersFactory, SizesFactory, DesignsFactory, ColorsFactory, PrintsFactory, YarnsFactory, $state, Flash) {

        var self = this;
        $scope.options = {};
        var lookup = {};
        self.cloth = {};
        //SizesFactory.getSizes(function (response) {
        //    $scope.options.sizes = response;
        //}, function (response) {
        //    Flash.create('danger', response.message, 'custom-class');
        //});

        self.cloth.delivery_date = ClothesFactory.getDate() || '';

        console.log(self.cloth.delivery_date);

        CustomersFactory.getCustomers(function (response) {
            $scope.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        YarnsFactory.getYarns(function (response) {
            $scope.options.yarns = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        $scope.customerSelected = function (customerId) {
            if (customerId) {
                //self.cloth.designId = {};
                DesignsFactory.getCustomerDesigns(customerId, function (response) {
                    $scope.options.designs = response;
                }, function (response) {
                    Flash.create('danger', response.message, 'custom-class');
                });
            }
        }

        $scope.yarnSelected = function (yarnId) {
            if (yarnId) {
                self.cloth.colorCode = '';
                $scope.fetchSize();
                ColorsFactory.getCustomerColors(yarnId, function (response) {
                    $scope.options.colors = response;
                    for (var i = 0, len = $scope.options.colors.length; i < len; i++) {
                        lookup[$scope.options.colors[i].id] = $scope.options.colors[i];
                    }
                }, function (response) {
                    Flash.create('danger', response.message, 'custom-class');
                })
            }
        }

        $scope.colorSelected = function (colorId) {
            $scope.$apply(self.cloth.colorCode = (lookup[colorId].code)
            );
        }

        $scope.fetchSize = function () {
            if (self.cloth.yarnId && self.cloth.designId) {
                SizesFactory.getSizesbyYarns(self.cloth.designId, self.cloth.yarnId, function (response) {
                    $scope.options.sizes = response;
                }, function (response) {
                    Flash.create('danger', response.message, 'custom-class');
                })
            }
        }


        PrintsFactory.getPrints(function (response) {
            $scope.options.prints = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        self.submitCloth = function () {
            ClothesFactory.createCloth(self.cloth, function (response) {
                $state.go('dashboard.clothes.index');
                Flash.create('success', 'New Cloth added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }

        self.deliveryDateSet = function () {
            ClothesFactory.setDate(self.cloth.delivery_date);
        }

        //date-picker

        self.minimum_date = new Date();
        $scope.datepickers = {
            from_date: false,
            to_date: false
        }
        //opens date picker
        $scope.open = function ($event, which) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.datepickers[which] = true;
        };
        $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd/MM/yyyy', 'shortDate'];
        $scope.format = $scope.formats[2];

    });
