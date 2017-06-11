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

        self.search = {};
        //SizesFactory.getSizes(function (response) {
        //    $scope.options.sizes = response;
        //}, function (response) {
        //    Flash.create('danger', response.message, 'custom-class');
        //});

        self.cloth.delivery_date = ClothesFactory.getDate() || '';

        self.types = [{id: 0, name: "Knitting"}, {id: 1, name: "Weaving"}];

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


        ColorsFactory.getColors(function (response) {
            $scope.options.colors = response;
            for (var i = 0, len = $scope.options.colors.length; i < len; i++) {
                lookup[$scope.options.colors[i].id] = $scope.options.colors[i];
                $scope.options.colors[i].name = $scope.options.colors[i].code;
            }
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        $scope.colorSelected = function (colorId) {
            $scope.fetchSize(colorId);
            $scope.$apply(function () {
                self.colorName_company = colorId == undefined ? undefined : (lookup[colorId].name_company);
            });

        }


        $scope.fetchSize = function (colorId) {
            if (colorId && self.cloth.designId) {
                var yarnId = lookup[colorId].yarnId;
                SizesFactory.getSizesbyYarns(self.cloth.designId, yarnId, function (response) {
                    $scope.options.sizes = response;
                }, function (response) {
                    Flash.create('danger', response.message, 'custom-class');
                })
            }
        }


        PrintsFactory.getPrints(function (response) {
            var prints = response;
            angular.forEach(prints, function (p) {
                p.name = p.name + "(" + p.sizeName + ")";
            })
            $scope.options.prints = prints;
        }, function (response) {


            Flash.create('danger', response.message, 'custom-class');
        })

        self.submitCloth = function () {
            ClothesFactory.createCloth(self.cloth, function (response) {
                Flash.create('success', 'New Cloth added successfully', 'custom-class');
                self.cloth.quantity = undefined;
                self.cloth.sizeId = undefined;

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
