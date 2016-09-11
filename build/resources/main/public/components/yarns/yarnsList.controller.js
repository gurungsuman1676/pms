'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('YarnsListCtrl', function (YarnsFactory, Flash) {

        var self = this;
        self.yarns = [];
        YarnsFactory.getYarns(function (response) {
            self.yarns = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
