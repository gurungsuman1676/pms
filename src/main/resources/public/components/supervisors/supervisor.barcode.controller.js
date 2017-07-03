'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('SupervisorsBarcodeCtrl', function (ClothesFactory, $localStorage, Flash) {

        var self = this;

        self.clothes = [];

        ClothesFactory.getClothesByBarcode({barcodes: $localStorage.shippedClothes}, function (clothes) {
            self.clothes = clothes;
        }, function (response) {
            Flash.create('danger', response.message, 'custom-class');
        });

        self.getByLabel = function (cloth, label) {
            var value = "";
            if (angular.isDefined(cloth.extraField)) {
                var array = cloth.extraField.split('--');
                for (var i = 0; i <= array.length; i++) {
                    if (array[i].indexOf(label) !== -1) {
                        var splitArray = "::";
                        value = splitArray[1];
                        break;
                    }
                }
            }
            return value;
        }
    });
