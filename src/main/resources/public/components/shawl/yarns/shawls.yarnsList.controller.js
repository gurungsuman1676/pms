'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsYarnsListCtrl', function (ShawlsYarnsFactory, Flash) {

        var self = this;
        self.yarns = [];
        ShawlsYarnsFactory.getYarns(function (response) {
            self.yarns = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
