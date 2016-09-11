'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('DesignsNewCtrl', function ($scope, DesignsFactory, CustomersFactory, $state, Flash) {

        var self = this;
        self.options = {};

        self.showCheckbox = true;

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

        self.submitDesign = function () {
            if (!self.design.parentName) {
                self.design.parentId = null;
            }
            DesignsFactory.createDesign(self.design, function (response) {
                self.options.designParents.push(response);
                Flash.create('success', 'New Design added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
