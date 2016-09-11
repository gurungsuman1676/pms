'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('LocationsListCtrl', function (LocationsFactory, Flash) {

        var self = this;
        self.locations = [];
        LocationsFactory.getLocations(function (response) {
            self.locations = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });
    });
