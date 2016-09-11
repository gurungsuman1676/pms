'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('UsersListCtrl', function (UsersFactory, Flash) {

        var self = this;
        self.users = [];
        UsersFactory.getUsers(function (response) {
            self.users = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
