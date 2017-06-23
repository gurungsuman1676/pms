'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('KnittersListCtrl', function (KnittersFactory, Flash) {

        var self = this;
        self.knitters = [];
        KnittersFactory.getKnitters(function (response) {
            self.knitters = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
