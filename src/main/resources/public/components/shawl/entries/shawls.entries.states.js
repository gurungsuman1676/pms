;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.shawls.entries', {
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
                                'components/shawl/customers/shawls.customers.factory.js',
                                'components/shawl/shawls/shawls.factory.js',
                                'components/shawl/sizes/shawls.sizes.factory.js',
                                'components/shawl/yarns/shawls.yarns.factory.js',
                                'components/locations/locations.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })

            .state('dashboard.shawls.entries.index', {
                url: '/shawls/entries',
                templateUrl: 'components/shawl/entries/index.html',
                controller: 'ShawlsEntriesCtrl',
                controllerAs: 'ctrl'
            })

            .state('dashboard.shawls.entries.new', {
                url: '/shawls/entries/new',
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