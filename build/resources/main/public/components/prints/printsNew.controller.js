'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('PrintsNewCtrl', function ($scope, PrintsFactory, SizesFactory, CurrenciesFactory, $state, Flash) {

        var self = this;
        self.options = {};

        SizesFactory.getSizes(function (response) {
            self.options.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CurrenciesFactory.getCurrencies(function (response) {
            self.options.currencies = response;
        }, function (resposne) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitPrint = function () {
            PrintsFactory.createPrint(self.print, function (response) {
                Flash.create('success', 'New Print added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
