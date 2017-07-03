'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SupervisorsCtrl', function ($scope, $state, SupervisorsFactory, Flash, $localStorage, ngTableParams, ClothesFactory, ngDialog, $window) {

        var self = this;
        self.cloth = {};
        self.isShippingUser = ($localStorage.user.roles).indexOf("SHIPPING") != -1;
        self .CUSTOMER_NAME = {
            ROBERT: 'Robert',
            PHILLIPE: 'Phillipe',
            DANNY: 'Danny'
        };


        // To check the delivery date
        var currentDate = new Date();
        $scope.warningDate = currentDate.setDate(currentDate.getDate() + 7);

        self.viewHistory = function (cloth) {
            var history;
            ClothesFactory.getHistory(cloth, function (response) {
                console.log(response);
                history = response;
                ngDialog.open({template: '/components/clothes/history.html', data: history});
            }, function (error) {
                history = error;
            });
        }

        self.viewDetails = function (cloth) {
            var url1 = 'http://localhost/customers/' + cloth.customer.name + '/' + cloth.price.designName + '.xls';
            var url2 = 'http://localhost/customers/' + cloth.customer.name + '/' + cloth.price.designName + '.xlsx';

            var request = new XMLHttpRequest();
            request.open('HEAD', url1, false);
            request.send();
            if (request.status == 200) {
                $window.open(url1);
            } else {
                $window.open(url2);
            }

        }

        self.generateBarCode = function () {
            $state.go("dashboard.supervisors.barcode");
        };

        self.submitCloth = function () {
            //TODO: change clothId to cloth.id
            SupervisorsFactory.updateLocation(self.clothId, self.cloth, function () {
                $localStorage.shippedClothes.push(self.clothId);
                self.clothId = '';
                $("#barcode").focus();
                Flash.create('success', 'Location for cloth added successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        };

    });
