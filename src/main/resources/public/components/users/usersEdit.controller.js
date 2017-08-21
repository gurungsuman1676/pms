'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('UsersEditCtrl', function ($stateParams, UsersFactory, LocationsFactory, $state, Flash) {

        var self = this;
        self.isEdit = true;


        self.roles = [{id: 'ADMIN', name: 'ADMIN'}, {id: 'USER', name: 'SUPERVISOR'}];

        LocationsFactory.getLocations({}, function (locations) {
            UsersFactory.getUser($stateParams.userId, function (response) {
                self.user = response;
                self.options.locations = angular.forEach(locations, function (l) {
                    l.name = l.name + " ( " + l.locationType + " )"
                    if(l.name === self.user.location){
                        self.user.location = l.id;
                    }

                });
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });

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
