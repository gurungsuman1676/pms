;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.shawls.sizes', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/shawl/sizes/shawls.sizesList.controller.js',
                                'components/shawl/sizes/shawls.sizesNew.controller.js',
                                'components/shawl/sizes/shawls.sizesEdit.controller.js',
                                'components/shawl/sizes/shawls.sizes.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.shawls.sizes.index', {
                url: '/shawls/sizes',
                templateUrl: 'components/shawl/sizes/index.html',
                controller: 'ShawlsSizesListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.sizes.new', {
                url: '/shawls/sizes/new',
                templateUrl: 'components/shawl/sizes/new.html',
                controller: 'ShawlsSizesNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.sizes.edit', {
                url: '/shawls/sizes/edit/{sizeId:string}',
                templateUrl: 'components/shawl/sizes/new.html',
                controller: 'ShawlsSizesEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();