'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SizesListCtrl', function (SizesFactory, Flash) {

        var self = this;
        self.sizes = [];
        SizesFactory.getSizes(function (response) {
            self.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
