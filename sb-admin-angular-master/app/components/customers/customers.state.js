;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.customers', {
                template: '<ui-view />',
                controller: 'CustomersParentCtrl',
                controllerAs: 'parentCtrl',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/customers/customersParent.controller.js',
                                'components/customers/customersList.controller.js',
                                'components/customers/customersNew.controller.js',
                                'components/customers/customersEdit.controller.js',
                                'components/customers/customers.factory.js',
                                'components/currencies/currencies.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.customers.index', {
                url: '/customers',
                templateUrl: 'components/customers/index.html',
                controller: 'CustomersListCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.customers.new', {
                url: '/customers/new',
                templateUrl: 'components/customers/new.html',
                controller: 'CustomersNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.customers.edit', {
                url: '/customers/edit/{customerId:string}',
                templateUrl: 'components/customers/new.html',
                controller: 'CustomersEditCtrl',
                controllerAs: 'ctrl',
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();