'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsEntriesNewCtrl', function (ShawlsEntriesFactory, Flash, ShawlsYarnsFactory,
                                               ShawlsColorsFactory, ShawlsCustomersFactory, ShawlsSizesFactory,
                                               ShawlsFactory, LocationsFactory, $state) {

        var self = this;
        self.options = {};
        self.options.yarns = [];
        ShawlsYarnsFactory.getYarns(function (response) {
            self.options.yarns = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.options.colors = [];
        ShawlsColorsFactory.getColors(function (response) {
            self.options.colors = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.options.customers = [];
        ShawlsCustomersFactory.getCustomers(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.options.sizes = [];
        ShawlsSizesFactory.getSizes(function (response) {
            self.options.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.options.shawls = [];
        ShawlsFactory.getShawls(function (response) {
            self.options.shawls = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.options.locations = [];
        LocationsFactory.getLocations({type: 'SHAWL'}, function (response) {
            self.options.locations = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitEntry = function () {
            ShawlsEntriesFactory.addEntry(self.entry, function () {
                $state.go('dashboard.shawls.entries.index');
                Flash.create('success', 'Entries are  updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }

    });
