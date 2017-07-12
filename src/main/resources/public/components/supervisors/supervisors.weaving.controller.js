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
                                                    ClothesFactory,
                                                    CustomersFactory,
                                                    Flash, ngTableParams, LocationsFactory, ngDialog,
                                                    DesignsFactory, $localStorage, PrintsFactory,SizesFactory) {

        var self = this;

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
        },function (response) {
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



    });
