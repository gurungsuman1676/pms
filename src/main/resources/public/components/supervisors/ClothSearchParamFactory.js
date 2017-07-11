;
(function () {
    'use strict';

    var ClothSearchParamFactory = function () {
        return {
            params: undefined,
            page: undefined,
            count: undefined
        };
    };

    ClothSearchParamFactory.$inject = [];
    angular.module('sbAdminApp')
        .factory('ClothSearchParamFactory', ClothSearchParamFactory);

}());