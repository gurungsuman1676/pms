;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.shawls.colors', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/shawl/colors/shawls.colorsList.controller.js',
                                'components/shawl/colors/shawls.colorsNew.controller.js',
                                'components/shawl/colors/shawls.colorsEdit.controller.js',
                                'components/shawl/colors/shawls.colors.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.shawls.colors.index', {
                url: '/shawls/colors',
                templateUrl: 'components/shawl/colors/index.html',
                controller: 'ShawlsColorsListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.colors.new', {
                url: '/shawls/colors/new',
                templateUrl: 'components/shawl/colors/new.html',
                controller: 'ShawlsColorsNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.colors.edit', {
                url: '/shawls/colors/edit/{colorId:string}',
                templateUrl: 'components/shawl/colors/new.html',
                controller: 'ShawlsColorsEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();