'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SupervisorsWeavingCtrl', function ($scope, SupervisorsFactory, Flash, ClothesFactory, $localStorage, $window, LocationsFactory) {

        var self = this;
        self.cloth = {};
        self.isShippingUser = ($localStorage.user.roles).indexOf("SHIPPING") != -1;

        self.locations = [];
        LocationsFactory.getLocations({type: 'WEAVING'},
            function (response) {
                self.locations = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });

        $scope.fetchCustomer = function () {
            self.cloth.customerId = undefined;
            $scope.options.customers = [];

            self.cloth.designId = undefined;
            $scope.options.designs = [];

            self.cloth.sizeId = undefined;
            $scope.options.sizes = [];

            self.cloth.printId = undefined;
            $scope.options.prints = [];

            self.cloth.extraField = undefined;
            $scope.options.extraFields = [];

            ClothesFactory.getCustomerByOrder(self.cloth.orderNo, function (response) {
                $scope.options.customers = response;

            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })

        }
        $scope.fetchDesigns = function () {
            self.cloth.designId = undefined;
            $scope.options.designs = [];

            self.cloth.sizeId = undefined;
            $scope.options.sizes = [];

            self.cloth.printId = undefined;
            $scope.options.prints = [];

            self.cloth.extraField = undefined;
            $scope.options.extraFields = [];

            ClothesFactory.getDesignsForWeaving(self.cloth.orderNo, self.cloth.customerId, function (response) {
                $scope.options.designs = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })

        }

        $scope.fetchPrints = function () {
            self.cloth.printId = undefined;
            $scope.options.prints = [];

            self.cloth.extraField = undefined;
            $scope.options.extraFields = [];

            ClothesFactory.getPrintForWeaving(self.cloth.orderNo, self.cloth.customerId, self.cloth.designId, self.cloth.sizeId, function (response) {
                $scope.options.prints = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })

        }
        $scope.fetchSizes = function () {
            self.cloth.sizeId = undefined;
            $scope.options.sizes = [];

            self.cloth.printId = undefined;
            $scope.options.prints = [];

            self.cloth.extraField = undefined;
            $scope.options.extraFields = [];

            ClothesFactory.getSizeForWeaving(self.cloth.orderNo, self.cloth.customerId, self.cloth.designId, function (response) {
                $scope.options.sizes = response;
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
        $scope.fetchExtraFields = function () {

            self.cloth.extraField = undefined;
            $scope.options.extraFields = [];

            ClothesFactory.getExtraFieldsForWeaving(self.cloth.orderNo, self.cloth.customerId, self.cloth.designId, self.cloth.sizeId, self.cloth.printId, function (response) {
                $scope.options.extraFields = [];
                angular.forEach(response, function (r) {
                    $scope.options.extraFields.push({
                        id: r,
                        name: r
                    })
                });
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }

        self.submitCloth = function () {
            ClothesFactory.updateWeavingLocation(self.cloth, function (response) {
                self.cloth.extraField = undefined;
                self.cloth.quantity = undefined;
                Flash.create('success', 'Cloth added to shipped ', 'custom-class');

            }, function (response) {
                $window.scrollTo(0, 0);
                Flash.create('danger', response.message, 'custom-class');
            })

        }
    });
