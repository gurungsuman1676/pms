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
        self.locationTypes = [
            {
                id: 'Shawl,',
                name: 'Shawl'
            },
            {
                id: 'KNITTING',
                name: 'KNITTING'
            }];

        LocationsFactory.getLocation($stateParams.sizeId, function (response) {
            self.location = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        })

        self.submitLocation = function () {
            LocationsFactory.updateSize(self.size, function (response) {
                $state.go('dashboard.locations.index');
                Flash.create('success', 'Location updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
