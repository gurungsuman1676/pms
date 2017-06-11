'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SupervisorsWeavingCtrl', function ($scope, SupervisorsFactory, Flash, ClothesFactory,$localStorage) {

        var self = this;
        self.cloth = {};
        self.isShippingUser = ($localStorage.user.roles).indexOf("SHIPPING") != -1;

        $scope.fetchCustomer = function () {
            ClothesFactory.getCustomerByOrder(self.cloth.orderNo, function (response) {
                $scope.options.customers = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })

        }
        $scope.fetchDesigns = function () {
            ClothesFactory.getDesignsForWeaving(self.cloth.orderNo, self.cloth.customerId, function (response) {
                $scope.options.designs = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })

        }

        $scope.fetchPrints = function () {
            ClothesFactory.getPrintForWeaving(self.cloth.orderNo, self.cloth.customerId, self.cloth.designId, self.cloth.sizeId, function (response) {
                $scope.options.prints = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })

        }
        $scope.fetchSizes = function () {
            ClothesFactory.getSizeForWeaving(self.cloth.orderNo, self.cloth.customerId, self.cloth.designId, function (response) {
                $scope.options.sizes = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }

        self.submitCloth = function () {
            ClothesFactory.updateWeavingShipping(self.cloth, function (response) {
                $scope.options.sizes = response;
                self.cloth.quantity = undefined;
                Flash.create('success', 'Cloth added to shipped ', 'custom-class');

            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })

        }
    });
