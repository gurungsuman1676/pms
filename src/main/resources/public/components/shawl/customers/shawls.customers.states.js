;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.shawls.customers', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/shawl/customers/shawls.customersList.controller.js',
                                'components/shawl/customers/shawls.customersNew.controller.js',
                                'components/shawl/customers/shawls.customersEdit.controller.js',
                                'components/shawl/customers/shawls.customers.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.shawls.customers.index', {
                url: '/shawls/customers',
                templateUrl: 'components/shawl/customers/index.html',
                controller: 'ShawlsCustomersListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.customers.new', {
                url: '/shawls/customers/new',
                templateUrl: 'components/shawl/customers/new.html',
                controller: 'ShawlsCustomersNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.shawls.customers.edit', {
                url: '/shawls/customers/edit/{customerId:string}',
                templateUrl: 'components/shawl/customers/new.html',
                controller: 'ShawlsCustomersEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();