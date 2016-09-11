'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('DesignsEditCtrl', function ($scope, $stateParams, DesignsFactory, CustomersFactory, $state, Flash) {

        var self = this;
        self.options = {};
        self.disableEdit = true;


        DesignsFactory.getDesign($stateParams.designId, function (response) {
            self.design = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        DesignsFactory.getDesignParents(function(response){
            self.options.designParents = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        CustomersFactory.getCustomers(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitDesign = function () {
            DesignsFactory.updateDesign(self.design, function (response) {
                $state.go('dashboard.designs.index');
                Flash.create('success', 'Design updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
