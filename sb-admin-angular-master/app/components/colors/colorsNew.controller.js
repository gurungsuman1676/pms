'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ColorsNewCtrl', function ($scope, ColorsFactory, CustomersFactory, YarnsFactory, $state, Flash) {

        var self = this;
        self.options = {};

        CustomersFactory.getCustomerParents(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        YarnsFactory.getYarns(function (response) {
            self.options.yarns = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitColor = function () {
            ColorsFactory.createColor(self.color, function (response) {
                Flash.create('success', 'New Color added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }

    });
