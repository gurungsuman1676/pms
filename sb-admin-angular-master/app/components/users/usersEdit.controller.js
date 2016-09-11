'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('UsersEditCtrl', function ($stateParams, UsersFactory, LocatonsFactory, $state, Flash) {

        var self = this;

        UsersFactory.getUser($stateParams.userId, function (response) {
            self.user = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        LocationsFactory.getLocations(function (response) {
            self.options.locations = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitUser = function () {
            UsersFactory.updateUser(self.user, function (response) {
                $state.go('dashboard.users.index');
                Flash.create('success', 'User updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
