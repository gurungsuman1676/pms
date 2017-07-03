;
(function () {
    'use strict';

    var ClothesFactory = function ($http, RESOURCES, $window, $timeout, Upload) {
        var factory = {};
        var deliverDate;

        factory.setDate = function (date) {
            deliverDate = date;
        }

        factory.getDate = function () {
            return deliverDate;
        }

        var dummyData = {
            data: [{name: 132}]
        }

        factory.getClothes = function (params, successCallback, errorCallback) {
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

        factory.getClothesByLocation = function (params, successCallback, errorCallback) {
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
                url: RESOURCES.apiURL + '/clothes/' + cloth.id + '/delete'
            }).success(function (response) {
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
                url: RESOURCES.apiURL + '/clothes/' + cloth.id + '/activities'
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

        factory.downloadOrderSheet = function (orderNo, customerId) {
            $window.open(RESOURCES.apiURL + '/clothes' + '/order_sheet' + '?orderNo=' + orderNo + '&customerId=' + customerId);
        }

        factory.downloadShippingList = function (shippingNumber) {
            $window.open(RESOURCES.apiURL + '/clothes' + '/shipping_list' + '?shippingNumber=' + shippingNumber);
        }

        factory.downloadPendingList = function (orderNo, customerId) {
            $window.open(RESOURCES.apiURL + '/clothes' + '/pending_list' + '?orderNo=' + orderNo + '&customerId=' + customerId);
        }

        factory.downloadInvoice = function (customerId, shippingNumber) {
            $window.open(RESOURCES.apiURL + '/clothes' + '/invoice' + '?customerId=' + customerId + '&shippingNumber=' + shippingNumber);
        }

        factory.downloadProformaInvoice = function (orderNo, customerId) {
            $window.open(RESOURCES.apiURL + '/clothes' + '/proforma_invoice' + '?orderNo=' + orderNo + '&customerId=' + customerId);
        }

        factory.getClothesReport = function (params) {
            $window.open(RESOURCES.apiURL + '/clothes' + '/report' + params);
        }
        factory.getWeavingReport = function (id) {
            $window.open(RESOURCES.apiURL + '/clothes' + '/weaving/' + id);
        }

        factory.uploadExcel = function (excel, type, successCallback, errorCallback) {
            var file = excel;
            if (file) {

                $http({
                    method: 'POST',
                    url: RESOURCES.apiURL + '/clothes' + '/excel-upload',
                    data: {file: file, type: type},
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    },
                    transformRequest: function (data, headersGetter) {
                        var formData = new FormData();
                        angular.forEach(data, function (value, key) {
                            formData.append(key, value);
                        });
                        var headers = headersGetter();
                        delete headers['Content-Type'];
                        return formData;
                    }
                }).success(function (response) {
                    successCallback(response);
                }).error(function (error) {
                    errorCallback(error);
                })
            }
        };
        factory.getCustomerByOrder = function (orderNumber, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes/customers',
                params: {orderNumber: orderNumber}
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };
        factory.getDesignsForWeaving = function (orderNumber, customerId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes/designs',
                params: {orderNumber: orderNumber, customerId: customerId}
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };
        factory.getSizeForWeaving = function (orderNumber, customerId, designId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes/sizes',
                params: {orderNumber: orderNumber, customerId: customerId, designId: designId}
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };
        factory.getPrintForWeaving = function (orderNumber, customerId, designId, sizeId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes/prints',
                params: {orderNumber: orderNumber, customerId: customerId, designId: designId, sizeId: sizeId}
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };

        factory.getExtraFieldsForWeaving = function (orderNumber, customerId, designId, sizeId, printId, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes/extraFields',
                params: {
                    orderNumber: orderNumber,
                    customerId: customerId,
                    designId: designId,
                    sizeId: sizeId,
                    printId: printId
                }
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };
        factory.getClothesByBarcode = function (barcodes, successCallback, errorCallback) {
            $http({
                method: 'GET',
                url: RESOURCES.apiURL + '/clothes/shipping/barcodes',
                params: barcodes
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            })
        };



        factory.updateWeavingShipping = function (cloth, successCallback, errorCallback) {
            $http({
                method: 'PUT',
                url: RESOURCES.apiURL + '/clothes/shipping',
                data: cloth
            }).success(function (response) {
                successCallback(response);
            }).error(function (response) {
                errorCallback(response);
            });
        }

        return factory;
    };

    ClothesFactory.$inject = ['$http', 'RESOURCES', '$window'];
    angular.module('sbAdminApp')
        .factory('ClothesFactory', ClothesFactory);

}());