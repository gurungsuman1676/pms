'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('LocationsNewCtrl', function (LocationsFactory, $state, Flash) {

        var self = this;
        self.locationTypes = [
            {
                id: 'WEAVING',
                name: 'WEAVING'
            },
            {
                id: 'KNITTING',
                name: 'KNITTING'
            }];
        self.submitLocation = function () {
            LocationsFactory.createLocation(self.location, function (response) {
                $state.go('dashboard.locations.index');
                Flash.create('success', 'New Location added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        }
    });
