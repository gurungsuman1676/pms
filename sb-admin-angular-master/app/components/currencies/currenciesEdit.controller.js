'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('CurrenciesEditCtrl', function ($stateParams, CurrenciesFactory, $state, Flash) {

        var self = this;

        CurrenciesFactory.getCurrency($stateParams.currencyId, function (response) {
            self.currency = response;
        }, function (response) {
            // TODO error handling;
            Flash.create('danger', response.message, 'custom-class');
        })

        self.submitCurrency = function () {
            CurrenciesFactory.updateCurrency(self.currency, function (response) {
                $state.go('dashboard.currencies.index');
                Flash.create('success', 'Currency updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
