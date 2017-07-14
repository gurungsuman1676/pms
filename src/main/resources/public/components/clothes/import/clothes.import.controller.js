'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('ClothesImportCtrl', function ($scope, ClothesFactory, $state, Flash) {
        var self = this;

        self.orderTypes = [
            {
                id: 'RE_ORDER',
                name: 'RE_ORDER'
            },
            {
                id: 'BULK',
                name: 'BULK'
            },
            {
                id: 'PHOTO_SHOOT',
                name: 'PHOTO_SHOOT'
            },
            {
                id: 'SAMPLE',
                name: 'SAMPLE'
            }
        ];


        self.templates = [
            {
                id: 'dannyTemplate',
                name: 'Danny 1st Template'
            },
            {
                id: 'rukelTemplate',
                name: 'Ruckel Template'
            },
            {
                id: 'phillipeTemplate',
                name: 'Phillipe Template'
            },
            {
                id: 'dannyWeavingTemplate',
                name: 'Danny 2nd Template'
            }
        ];

        self.types = [
            {id: 0, name: "Knitting"},
            {id: 1, name: "Weaving"}
        ];

        $scope.isProcessing = false;

        self.importFromExcel = function (file, type, orderType, templateType) {
            if (file && type && orderType && templateType) {
                $scope.isProcessing = true;
                ClothesFactory.uploadExcel(file, type, orderType, templateType, function (success) {
                    $scope.isProcessing = false;
                        $state.go('dashboard.clothes.index');
                        Flash.create('success', 'Cloth added successfully', 'custom-class');
                    }, function (error) {
                    $scope.isProcessing = false;
                        Flash.create('danger', error.message, 'custom-class');
                    }
                )

            }else{
                Flash.create('danger', 'Please enter all the fields.', 'custom-class');

            }

        }

    });
