;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.inventory', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/shawl/entries/shawls.entries.controller.js',
                                'components/shawl/entries/shawls.entriesNew.controller.js',
                                'components/shawl/entries/shawls.entries.factory.js',
                                'components/shawl/colors/shawls.colors.factory.js',
                                'components/designs/designs.factory.js',
                                'components/sizes/sizes.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })

            .state('dashboard.inventory.index', {
                url: '/inventory',
                templateUrl: 'components/shawl/entries/index.html',
                controller: 'ShawlsEntriesCtrl',
                controllerAs: 'ctrl'
            })

            .state('dashboard.inventory.new', {
                url: '/inventory/new',
                templateUrl: 'components/shawl/entries/new.html',
                controller: 'ShawlsEntriesNewCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();