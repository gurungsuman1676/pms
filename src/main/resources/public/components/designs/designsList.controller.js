'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('DesignsListCtrl', function ($scope, DesignsFactory, ngTableParams, CustomersFactory, $filter, $modal, DesignsPropertiesFactory) {

        var self = this;
        self.designs = [];
        self.options = {};

        self.reloadTable = function () {
            self.designListTable.$params.page = 1;
            self.designListTable.reload();
        };

        self.filterParams = {
            name: undefined,
            customerId: undefined,
            setting: undefined,
            gauge: undefined
        };

        CustomersFactory.getCustomers(function (response) {
            self.options.customers = response;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.getDesignProperty = function (designId) {
            $modal.open({
                templateUrl: '/components/designs/property/index.html',
                size: 'lg',
                resolve: {
                    designId: function () {
                        return designId;
                    }
                },
                controller: ['$scope', 'DesignsPropertiesFactory', 'designId', '$modalInstance', 'Flash', function ($scope, DesignsPropertiesFactory, designId, $modalInstance, Flash) {
                    $scope.properties = [];

                    $scope.isEdit = false;
                    DesignsPropertiesFactory.getProperties(designId, function (response) {
                        $scope.properties = response;
                    }, function (response) {
                        Flash.create('danger', response.message, 'custom-class');

                    });

                    $scope.submitProperties = function () {
                        DesignsPropertiesFactory.createProperties(designId, $scope.properties,
                            function (response) {
                                Flash.create('success', 'Properties updated successfully', 'custom-class');
                                $modalInstance.dismiss();
                            }, function (response) {
                                Flash.create('danger', response.message, 'custom-class');
                                $modalInstance.dismiss();

                            });

                    };

                    $scope.addData = function () {
                        $scope.properties.push({
                            name: '',
                            value: '',
                            isEdit: false,
                            isAdd: true
                        });
                    };

                    $scope.canAddData = function () {
                        var valid = true;
                        angular.forEach($scope.properties, function (p) {
                            valid = valid && angular.isDefined(p.name) && p.name.length > 0 && angular.isDefined(p.value) && p.value.length > 0;
                        });
                        return valid;
                    };

                    $scope.deleteData = function (item) {
                        var index = $scope.properties.indexOf(item);
                        $scope.properties.splice(index, 1);
                    }
                }]
            });
        };

        var getFilterParams = function () {
            var filterParams = {};
            if (angular.isDefined(self.filterParams.name) && self.filterParams.name.length > 0) {
                filterParams.name = self.filterParams.name;
            }
            if (angular.isDefined(self.filterParams.gauge) && self.filterParams.gauge.length > 0) {
                filterParams.gauge = self.filterParams.gauge;
            }
            if (angular.isDefined(self.filterParams.setting) && self.filterParams.setting.length > 0) {
                filterParams.setting = self.filterParams.setting;
            }
            if (angular.isDefined(self.filterParams.customerId) && self.filterParams.customerId.length > 0 && self.filterParams.customerId != null) {
                filterParams.customerId = self.filterParams.customerId;
            }
            return filterParams;
        }

        DesignsFactory.getDesigns(function (response) {
            self.designs = response;
            self.reloadTable();
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.designListTable = new ngTableParams(
            {
                page: 1,
                count: 20
            }, {
                total: self.designs.length,
                getData: function ($defer, params) {
                    var orderedData = self.designs;
                    orderedData = $filter('filter')(orderedData, getFilterParams());
                    params.total(orderedData.length);
                    $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                }
            });
    });