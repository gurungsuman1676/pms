;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.colors', {
                template: '<ui-view />',
                //controller: 'ColorsParentCtrl',
                //controllerAs: 'parentctrl',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/shared/resources.js',
                                'components/yarns/yarns.factory.js',
                                'components/colors/colors.factory.js',
                                'components/colors/colorsNew.controller.js',
                                'components/customers/customers.factory.js',
                                'components/colors/colorsList.controller.js',
                                'components/colors/colorsEdit.controller.js',
                                'components/colors/colorsParent.controller.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.colors.index', {
                url: '/colors',
                templateUrl: 'components/colors/index.html',
                controller: 'ColorsListCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.colors.new', {
                url: '/colors/new',
                templateUrl: 'components/colors/new.html',
                controller: 'ColorsNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.colors.edit', {
                url: '/colors/edit/{colorId:string}',
                templateUrl: 'components/colors/new.html',
                controller: 'ColorsEditCtrl',
                controllerAs: 'ctrl',
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();