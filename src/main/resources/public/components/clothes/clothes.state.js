;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.clothes', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
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
                                'components/supervisors/ClothSearchParamFactory.js',
                                'components/locations/locations.factory.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.clothes.index', {
                url: '/clothes',
                templateUrl: 'components/clothes/index.html',
                controller: 'ClothesListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.clothes.new', {
                url: '/clothes/new',
                templateUrl: 'components/clothes/new.html',
                controller: 'ClothesNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.clothes.edit', {
                url: '/clothes/edit/{clothId:string}',
                templateUrl: 'components/clothes/new.html',
                controller: 'ClothesEditCtrl',
                controllerAs: 'ctrl',
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();