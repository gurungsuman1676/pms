//'use strict';
///**
// * @ngdoc function
// * @name sbAdminApp.controller:MainCtrl
// * @description
// * # MainCtrl
// * Controller of the sbAdminApp
// */
//angular.module('sbAdminApp')
//    .controller('PricesParentCtrl', function ($scope, $stateParams, CustomersFactory, PricesFactory, ColorsFactory, SizesFactory, DesignsFactory, $state, Flash) {
//        var self = this;
//        self.options = {};
//        SizesFactory.getSizes(function (response) {
//            self.options.sizes = response;
//        }, function (response) {
//            Flash.create('danger', response.message, 'custom-class');
//        });
//
//
//        CustomersFactory.getCustomerParents(function (response) {
//            self.options.customers = response;
//        }, function (response) {
//            Flash.create('danger', response.message, 'custom-class');
//        })
//
//        DesignsFactory.getDesigns(function (response) {
//            self.options.designs = response;
//        }, function (response) {
//            Flash.create('danger', response.message, 'custom-class');
//        })
//
//        self.getDesigns = function (customerId) {
//            DesignsFactory.getCustomerDesigns(customerId, function (response) {
//                self.options.designs = response;
//            }, function (response) {
//                Flash.create('danger', response.message, 'custom-class');
//            })
//
//            ColorsFactory.getCustomerColors(customerId, function (response) {
//                self.options.colors = response;
//            }, function (response) {
//                Flash.create('danger', response.message, 'custom-class');
//            })
//        }
//
//
//
//    });
