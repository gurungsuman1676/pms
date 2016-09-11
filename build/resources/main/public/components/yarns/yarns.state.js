;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.yarns', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/yarns/yarnsList.controller.js',
                                'components/yarns/yarnsNew.controller.js',
                                'components/yarns/yarnsEdit.controller.js',
                                'components/yarns/yarns.factory.js',
                                'components/customers/customers.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.yarns.index', {
                url: '/yarns',
                templateUrl: 'components/yarns/index.html',
                controller: 'YarnsListCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.yarns.new', {
                url: '/yarns/new',
                templateUrl: 'components/yarns/new.html',
                controller: 'YarnsNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.yarns.edit', {
                url: '/yarns/edit/{yarnId:string}',
                templateUrl: 'components/yarns/new.html',
                controller: 'YarnsEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();