'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsListCtrl', function (ShawlsFactory, Flash) {

        var self = this;
        self.shawls = [];
        ShawlsFactory.getShawls(function (response) {
            self.shawls = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
