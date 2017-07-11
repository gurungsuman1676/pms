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
                                'components/supervisors/supervisors.weaving.controller.js',
                                'components/supervisors/supervisors.factory.js',
                                'components/clothes/clothesList.controller.js',
                                'components/clothes/clothesNew.controller.js',
                                'components/clothes/clothesEdit.controller.js',
                                'components/customers/customers.factory.js',
                                'components/clothes/clothes.factory.js',
                                'components/designs/designs.factory.js',
                                'components/colors/colors.factory.js',
                                'components/sizes/sizes.factory.js',
                                'components/prints/prints.factory.js',
                                'components/yarns/yarns.factory.js',
                                'components/shared/resources.js',
                                'components/locations/locations.factory.js',
                                'components/supervisors/ClothSearchParamFactory.js'

                            ]
                        })
                    }
                }
            })
            .state('dashboard.supervisors.index', {
                url: '/enter-knitting-location',
                templateUrl: 'components/supervisors/index.html',
                controller: 'SupervisorsCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.supervisors.weaving', {
                url: '/enter-weaving-location',
                templateUrl: 'components/supervisors/weaving.html',
                controller: 'SupervisorsWeavingCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.supervisors.clothes', {
                url: '/supervisor/clothes',
                templateUrl: 'components/supervisors/clothes.html',
                controller: 'ClothesListCtrl',
                controllerAs: 'ctrl'
            });
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();