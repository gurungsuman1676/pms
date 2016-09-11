'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('DesignsParentCtrl', function (DesignsFactory, CustomersFactory, $state, Flash) {

        var self = this;
        self.options = {};

        DesignsFactory.getDesignParents(function(response){
            self.options.designParents = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CustomersFactory.getCustomerParents(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

    });
