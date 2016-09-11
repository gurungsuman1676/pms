;
(function () {
    'use strict';

    var Resources = function () {
        return {
            apiURL: '/api/v1',
            STATUSES: {
                NOTFOUND: 404
            }
        }
    };

    angular
        .module('sbAdminApp')
        .constant('RESOURCES', Resources());

})();