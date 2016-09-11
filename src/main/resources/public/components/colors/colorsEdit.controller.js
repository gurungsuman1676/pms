'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ColorsEditCtrl', function ($scope, $stateParams, ColorsFactory, CustomersFactory, YarnsFactory, $state, Flash) {

        var self = this;

        self.options = {};

        ColorsFactory.getColor($stateParams.colorId, function (response) {
            self.color = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

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
            ColorsFactory.updateColor(self.color, function (response) {
                $state.go('dashboard.colors.index');
                Flash.create('success', 'Color updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
