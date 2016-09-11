;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.supervisors', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/supervisors/supervisors.controller.js',
                                'components/supervisors/supervisors.factory.js',
                                'components/clothes/clothes.factory.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.supervisors.index', {
                url: '/enter-location',
                templateUrl: 'components/supervisors/index.html',
                controller: 'SupervisorsCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.supervisors.clothes', {
                url: '/supervisor/clothes',
                templateUrl: 'components/supervisors/clothes.html',
                controller: 'SupervisorsCtrl',
                controllerAs: 'ctrl'
            });
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();