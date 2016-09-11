'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('CurrenciesNewCtrl', function (CurrenciesFactory, $state, Flash) {

        var self = this;
        self.submitCurrency = function () {
            CurrenciesFactory.createCurrency(self.currency, function (response) {
                Flash.create('success', 'New Currency added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
