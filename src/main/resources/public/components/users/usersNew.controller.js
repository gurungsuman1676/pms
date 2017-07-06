'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('UsersNewCtrl', function (UsersFactory, LocationsFactory, $state, Flash) {

        var self = this;
        self.options = {};
        self.roles = [{id: 'ADMIN', name: 'ADMIN'}, {id: 'USER', name: 'SUPERVISOR'}];

        LocationsFactory.getLocations({}, function (response) {
            self.options.locations = angular.forEach(response, function (l) {
                l.name = l.name + " ( " + l.locationType + " )"
            });
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitUser = function () {
            UsersFactory.createUser(self.user, function (response) {
                $state.go('dashboard.users.index');
                Flash.create('success', 'New User added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
