;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.currencies', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/currencies/currenciesList.controller.js',
                                'components/currencies/currenciesNew.controller.js',
                                'components/currencies/currenciesEdit.controller.js',
                                'components/currencies/currencies.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.currencies.index', {
                url: '/currencies',
                templateUrl: 'components/currencies/index.html',
                controller: 'CurrenciesListCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.currencies.new', {
                url: '/currencies/new',
                templateUrl: 'components/currencies/new.html',
                controller: 'CurrenciesNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.currencies.edit', {
                url: '/currencies/edit/{currencyId:string}',
                templateUrl: 'components/currencies/new.html',
                controller: 'CurrenciesEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();