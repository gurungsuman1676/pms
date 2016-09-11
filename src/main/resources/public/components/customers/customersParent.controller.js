'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('CustomersParentCtrl', function ($scope, $stateParams, CustomersFactory, $state, Flash) {

        var self = this;
        self.options = {};

        CustomersFactory.getCustomerParents(function (response) {
            self.options.customerParents = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

    });
