'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SupervisorsCtrl', function ($scope, SupervisorsFactory, Flash,$localStorage) {

        var self = this;
        self.cloth = {
            shippingNumber: undefined,
            boxNumber: undefined

        };
        self.isShippingUser = ($localStorage.user.roles).indexOf("SHIPPING") != -1;

        self.submitCloth = function () {
            SupervisorsFactory.updateLocation(self.clothId, self.cloth, function (cloth) {
                self.clothId = '';
                $("#barcode").focus();
                Flash.create('success', 'Cloth  of ' +
                    cloth.customer.name + ' with ' +
                    'Design Name \'' + cloth.price.designName + ' \'' +
                    'Size \'' + cloth.price.sizeName + ' \'' +
                    'Color \'' + cloth.colorName + ' \'' +
                    'location updated  successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            });
        };

    });
