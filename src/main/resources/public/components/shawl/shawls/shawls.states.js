;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.shawls', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/shawl/shawls/shawlsList.controller.js',
                                'components/shawl/shawls/shawlsNew.controller.js',
                                'components/shawl/shawls/shawlsEdit.controller.js',
                                'components/shawl/shawls/shawls.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.shawls.index', {
                url: '/shawls',
                templateUrl: 'components/shawl/shawls/index.html',
                controller: 'ShawlsListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.new', {
                url: '/shawls/new',
                templateUrl: 'components/shawl/shawls/new.html',
                controller: 'ShawlsNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.edit', {
                url: '/shawls/edit/{shawlId:string}',
                templateUrl: 'components/shawl/shawls/new.html',
                controller: 'ShawlsEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();