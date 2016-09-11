//'use strict';
///**
// * @ngdoc function
// * @name sbAdminApp.controller:MainCtrl
// * @description
// * # MainCtrl
// * Controller of the sbAdminApp
// */
//angular.module('sbAdminApp')
//    .controller('PrintsParentCtrl', function (PrintsFactory, SizesFactory, CurrenciesFactory, $state, Flash) {
//
//        var self = this;
//
//        self.options = {};
//        SizesFactory.getSizes(function (response) {
//            self.options.sizes = response;
//        }, function (response) {
//            Flash.create('danger', response.message, 'custom-class');
//        });
//
//        CurrenciesFactory.getCurrencies(function (response) {
//            self.options.currencies = response;
//        }, function (resposne) {
//            Flash.create('danger', response.message, 'custom-class');
//        });
//    });
