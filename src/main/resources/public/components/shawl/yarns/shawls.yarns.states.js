;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.shawls.yarns', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/shawl/yarns/shawls.yarnsList.controller.js',
                                'components/shawl/yarns/shawls.yarnsNew.controller.js',
                                'components/shawl/yarns/shawls.yarnsEdit.controller.js',
                                'components/shawl/yarns/shawls.yarns.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.shawls.yarns.index', {
                url: '/shawls/yarns',
                templateUrl: 'components/shawl/yarns/index.html',
                controller: 'ShawlsYarnsListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.yarns.new', {
                url: '/shawls/yarns/new',
                templateUrl: 'components/shawl/yarns/new.html',
                controller: 'ShawlsYarnsNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.yarns.edit', {
                url: '/shawls/yarns/edit/{yarnId:string}',
                templateUrl: 'components/shawl/yarns/new.html',
                controller: 'ShawlsYarnsEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();