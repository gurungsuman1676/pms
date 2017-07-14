;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.clothes.import', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/clothes/clothes.factory.js',
                                'components/shared/resources.js',
                                'components/clothes/import/clothes.import.controller.js'

                            ]
                        })
                    }
                }
            })
            .state('dashboard.clothes.import.index', {
                url: '/clothes/import',
                templateUrl: 'components/clothes/import/index.html',
                controller: 'ClothesImportCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();