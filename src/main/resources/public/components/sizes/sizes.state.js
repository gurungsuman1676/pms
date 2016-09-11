;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.sizes', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/sizes/sizesList.controller.js',
                                'components/sizes/sizesNew.controller.js',
                                'components/sizes/sizesEdit.controller.js',
                                'components/sizes/sizes.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.sizes.index', {
                url: '/sizes',
                templateUrl: 'components/sizes/index.html',
                controller: 'SizesListCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.sizes.new', {
                url: '/sizes/new',
                templateUrl: 'components/sizes/new.html',
                controller: 'SizesNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.sizes.edit', {
                url: '/sizes/edit/{sizeId:string}',
                templateUrl: 'components/sizes/new.html',
                controller: 'SizesEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();