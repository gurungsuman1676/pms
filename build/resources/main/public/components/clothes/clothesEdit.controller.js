'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ClothesEditCtrl', function ($scope, $stateParams, ClothesFactory, PricesFactory, ColorsFactory, SizesFactory, DesignsFactory, $state, Flash) {

        var self = this;

        $scope.clothes = [];

        SizesFactory.getCloth($stateParams.clothId, function (response) {
            self.cloth = response;

        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        //SizesFactory.getSizes(function (response) {
        //    $scope.clothes = response;
        //    $scope.fetchSize();
        //}, function (response) {
        //    Flash.create('danger', response.message, 'custom-class');
        //});

        ColorsFactory.getColors(function (response) {
            $scope.colors = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        YarnsFactory.getYarns(function (response) {
            self.options.yarns = response;
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

        ClothesFactory.getPrice($stateParams.priceId, function (response) {
            self.price = response;
            if(self.price.sizeName) {
                self.price.size = self.price.sizeName;
            }
            if(self.price.colorName) {
                self.price.color = self.price.colorName;
            }
            if(self.price.designName) {
                self.price.design = self.price.designName;
            }
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        $scope.fetchSize = function() {
            if(self.cloth.yarnId && self.cloth.designId){
                SizesFactory.getSizes(self.cloth.yarnId, self.cloth.designId, function (response) {
                    $scope.options.sizes = response;
                }, function (response) {
                    Flash.create('danger', response.message, 'custom-class');
                })
            }
        }

        self.submitCloth = function () {
            ClothesFactory.updateCloth(self.cloth, function (response) {
                $state.go('dashboard.clothes.index');
                Flash.create('success', 'Cloth updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
