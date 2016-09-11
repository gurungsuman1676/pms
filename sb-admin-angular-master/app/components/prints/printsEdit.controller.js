'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('PrintsEditCtrl', function ($scope, $stateParams, PrintsFactory, SizesFactory, CurrenciesFactory, $state, Flash) {

        var self = this;
        self.options = {};

        PrintsFactory.getPrint($stateParams.printId, function (response) {
            self.print = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

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
            PrintsFactory.updatePrint(self.print, function (response) {
                $state.go('dashboard.prints.index');
                Flash.create('success', 'Print updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
