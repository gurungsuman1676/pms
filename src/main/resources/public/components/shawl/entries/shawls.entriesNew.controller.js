'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsEntriesNewCtrl', function (ShawlsEntriesFactory, Flash,
                                                  ShawlsColorsFactory, DesignsFactory, SizesFactory, $state) {

        var self = this;
        self.options = {};

        self.options.colors = [];
        ShawlsColorsFactory.getColors(function (response) {
            self.options.colors = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });


        self.options.sizes = [];
        SizesFactory.getSizes(function (response) {
            self.options.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.options.designs = [];
        DesignsFactory.getDesigns(function (response) {
            self.options.designs = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.options.locations = [
            {name:"ORDER-IN",id:"ORDER-IN"}, {name:"ORDER-OUT",id:"ORDER-OUT"}
        ];

        self.submitEntry = function () {
            ShawlsEntriesFactory.addEntry(self.entry, function () {
                $state.go('dashboard.inventory.index');
                Flash.create('success', 'Entries are  updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }

    });
