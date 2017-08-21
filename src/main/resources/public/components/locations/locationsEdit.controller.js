'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('LocationsEditCtrl', function ($stateParams, LocationsFactory, $state, Flash) {

        var self = this;
        self.edit = true;
        self.locationTypes = [
            {
                id: 'WEAVING',
                name: 'WEAVING'
            },
            {
                id: 'KNITTING',
                name: 'KNITTING'
            }]

        LocationsFactory.getLocation($stateParams.locationId, function (response) {
            self.location = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        self.submitLocation = function () {
            LocationsFactory.updateLocation(self.location, function (response) {
                $state.go('dashboard.locations.index');
                Flash.create('success', 'Location updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
