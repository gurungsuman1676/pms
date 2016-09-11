;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.prices', {
                template: '<ui-view />',
                //controller: 'PricesParentCtrl',
                //controllerAs: 'parentctrl',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/prices/pricesParent.controller.js',
                                'components/prices/pricesList.controller.js',
                                'components/prices/pricesNew.controller.js',
                                'components/prices/pricesEdit.controller.js',
                                'components/prices/prices.factory.js',
                                'components/customers/customers.factory.js',
                                'components/yarns/yarns.factory.js',
                                'components/designs/designs.factory.js',
                                'components/colors/colors.factory.js',
                                'components/sizes/sizes.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.prices.index', {
                url: '/prices',
                templateUrl: 'components/prices/index.html',
                controller: 'PricesListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.prices.new', {
                url: '/prices/new',
                templateUrl: 'components/prices/new.html',
                controller: 'PricesNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.prices.edit', {
                url: '/prices/edit/{priceId:string}',
                templateUrl: 'components/prices/new.html',
                controller: 'PricesEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();