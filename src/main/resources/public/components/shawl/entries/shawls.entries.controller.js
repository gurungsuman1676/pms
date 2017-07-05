'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ShawlsEntriesCtrl', function ($scope, ShawlsEntriesFactory, Flash, ShawlsColorsFactory, SizesFactory, DesignsFactory,ngTableParams) {

        var self = this;
        self.filtersOptions = {};
        self.filtersOptions.yarns = [];
        self.filterParams = {};

        self.filtersOptions.colors = [];
        ShawlsColorsFactory.getColors(function (response) {
            self.filtersOptions.colors = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });


        self.filtersOptions.sizes = [];
        SizesFactory.getSizes(function (response) {
            self.filtersOptions.sizes = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });


        self.filtersOptions.designs = [];
        DesignsFactory.getDesigns( function (response) {
            self.filtersOptions.designs = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });


        self.reloadTable = function () {
            self.entryListTable.$params.page = 1;
            self.entryListTable.reload();
        };

        self.generateCSV = function () {
            ShawlsEntriesFactory.getReport("?" +
                (angular.isDefined(self.filterParams.sizeId) ? "&sizeId= " + self.filterParams.sizeId : "") +
                (angular.isDefined(self.filterParams.designId) ? "&designId= " + self.filterParams.designId : "") +
                (angular.isDefined(self.filterParams.colorId) ? "&colorId= " + self.filterParams.colorId : ""));
        };


        self.entryListTable = new ngTableParams(
            {page: 1, count: 20},
            {
                total: 0,
                getData: function ($defer, params) {
                    var page = params.page();
                    ShawlsEntriesFactory.getEntries({
                            designId: self.filterParams.designId,
                            colorId: self.filterParams.colorId,
                            sizeId: self.filterParams.sizeId,
                            sort: 'lastModified,desc',
                            page: page - 1,
                            size: params.count()
                        },
                        function (response) {
                            self.noResults = response.data.length === 0;
                            self.entryListTable.total(response.totalElements);
                            $defer.resolve(response.data);
                        }, function (response) {
                            Flash.create('danger', response.message, 'custom-class');
                        });
                }
            }
        );
    });
