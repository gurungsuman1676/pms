'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('CurrenciesListCtrl', function (CurrenciesFactory, Flash) {

        var self = this;
        self.sizes = [];
        CurrenciesFactory.getCurrencies(function (response) {
            self.currencies = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
