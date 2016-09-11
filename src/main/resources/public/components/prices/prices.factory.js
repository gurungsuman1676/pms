;
(function () {
    'use strict';

    var PricesFactory = function ($http, RESOURCES) {
        var factory = {};

        factory.getPrices = function (successCallback, errorCallback) {

            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/prices'
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };

        factory.getPrice = function (priceId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/prices/' + priceId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }


        factory.createPrice = function (price, successCallback, errorCallback) {
            if (price.size) {
                price.sizeId = price.size.id;
            }
            if (price.color) {
                price.colorId = price.color.id;
            }
            if (price.design) {
                price.designId = price.design.id;
            }

            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/prices',
                data: price
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updatePrice = function (price, successCallback, errorCallback) {
            if (typeof (price.size) !== null && typeof (price.size) === 'object') {
                price.sizeId = price.size.id;
            }
            if (typeof (price.color) !== null && typeof (price.color) === 'object') {
                price.colorId = price.color.id;
            }
            if (typeof (price.design) !== null && typeof (price.design) === 'object') {
                price.designId = price.design.id;
            }

            console.log(price);

            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/prices/' + price.id,
                data: price
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }


        return factory;
    };

    PricesFactory.$inject = ['$http', 'RESOURCES'];
    angular.module('sbAdminApp')
        .factory('PricesFactory', PricesFactory);

}());