;
(function () {
    'use strict';

    var ClothesFactory = function ($http, RESOURCES,$window) {
        var factory = {};
        var deliverDate;

        factory.setDate = function(date) {
            deliverDate = date;
        }

        factory.getDate = function() {
            return deliverDate;
        }

        var dummyData = {
            data: [{name: 132}]
        }

        factory.getClothes = function (params,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes',
                params: params
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };

        factory.getClothesByLocation = function (params,successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes',
                params: params
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };



        factory.getCloth = function (clothId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes/' + clothId,
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }


        factory.createCloth = function (cloth, successCallback, errorCallback) {
            if (cloth.size) {
                cloth.sizeId = cloth.size.id;
            }
            if (cloth.color) {
                cloth.colorId = cloth.color.id;
            }
            if (cloth.design) {
                cloth.designId = cloth.design.id;
            }

            if (cloth.print) {
                cloth.printId = cloth.print.id;
            }

            $http({
                method: 'POST',
                url: RESOURCES.apiURL + '/clothes',
                data: cloth
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.updateCloth = function (cloth, successCallback, errorCallback) {
            if (typeof (cloth.size) !== null && typeof (cloth.size) === 'object') {
                cloth.sizeId = cloth.size.id;
            }
            if (typeof (cloth.color) !== null && typeof (cloth.color) === 'object') {
                cloth.colorId = cloth.color.id;
            }
            if (typeof (cloth.design) !== null && typeof (cloth.design) === 'object') {
                cloth.designId = cloth.design.id;
            }

            if (typeof (cloth.print) !== null && typeof (cloth.print) === 'object') {
                cloth.printId = cloth.print.id;
            }

            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/clothes/' + cloth.id,
                data: cloth
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        factory.deleteCloth = function (cloth, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/clothes/' + cloth.id +'/delete'
            }).success(function (response){
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        }

        factory.getHistory = function (cloth, successCallback, errorCallback) {
            // TODO: replace this code.
            // successCallback([{location: 'Linking', created: '2016/7/19'}, {location: 'Washing', created: '2016/7/21'}])
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes' + cloth.id + '/activities'
            }).success(function (response) {
                successCallback(response);
            }).error(function (error) {
                errorCallback(error);
            })
        }

        // factory.showDetails = function(cloth){
        //     console.log(cloth.customer.name);
        //     console.log(cloth.price.designName);
        //     $window.open('file://Desktop/' + cloth.customer.name+"/"+cloth.price.designName + '.xls');
        // }

        factory.downloadOrderSheet = function (orderNo,customerId) {
            $window.open(RESOURCES.apiURL + '/clothes' +'/order_sheet'+'?orderNo='+orderNo+'&customerId='+customerId);
        }

        factory.downloadShippingList = function (shippingNumber) {
            $window.open(RESOURCES.apiURL + '/clothes' +'/shipping_list'+'?shippingNumber='+shippingNumber);
        }

        factory.downloadPendingList = function (orderNo,customerId) {
            $window.open(RESOURCES.apiURL + '/clothes' +'/pending_list'+'?orderNo='+orderNo+'&customerId='+customerId);
        }

        factory.downloadInvoice = function (orderNo,customerId) {
            $window.open(RESOURCES.apiURL + '/clothes' +'/invoice'+'?orderNo='+orderNo+'&customerId='+customerId);
        }

        factory.downloadProformaInvoice = function (orderNo,customerId) {
            $window.open(RESOURCES.apiURL + '/clothes' +'/proforma_invoice'+'?orderNo='+orderNo+'&customerId='+customerId);
        }

        factory.getClothesReport = function (params) {
            $window.open(RESOURCES.apiURL + '/clothes' +'/report'+params);
        }

        return factory;
    };

    ClothesFactory.$inject = ['$http', 'RESOURCES','$window'];
    angular.module('sbAdminApp')
        .factory('ClothesFactory', ClothesFactory);

}());