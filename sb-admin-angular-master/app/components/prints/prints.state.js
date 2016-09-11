;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true
        });
        $stateProvider
            .state('dashboard.prints', {
                template: '<ui-view />',
                //controller: 'PrintsParentCtrl',
                //controllerAs: 'parentctrl',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/prints/printsParent.controller.js',
                                'components/prints/prints.factory.js',
                                'components/sizes/sizes.factory.js',
                                'components/currencies/currencies.factory.js',
                                'components/shared/resources.js',
                                'components/prints/printsList.controller.js',
                                'components/prints/printsNew.controller.js',
                                'components/prints/printsEdit.controller.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.prints.index', {
                url: '/prints',
                templateUrl: 'components/prints/index.html',
                controller: 'PrintsListCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.prints.new', {
                url: '/prints/new',
                templateUrl: 'components/prints/new.html',
                controller: 'PrintsNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.prints.edit', {
                url: '/prints/edit/{printId:string}',
                templateUrl: 'components/prints/new.html',
                controller: 'PrintsEditCtrl',
                controllerAs: 'ctrl',
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();