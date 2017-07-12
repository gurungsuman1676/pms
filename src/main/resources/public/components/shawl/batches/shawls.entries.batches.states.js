;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.inventory.batches', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/shawl/batches/shawls.entries.batches.controller.js',
                                'components/shawl/batches/shawls.entries.batches.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })

            .state('dashboard.inventory.batches.index', {
                url: '/inventory/{inventoryId:string}/batches',
                templateUrl: 'components/shawl/batches/index.html',
                controller: 'ShawlsEntriesBatchesCtrl',
                controllerAs: 'ctrl'
            })

    };
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();