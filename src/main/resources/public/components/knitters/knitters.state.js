;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.knitters', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/knitters/knittersList.controller.js',
                                'components/knitters/knittersNew.controller.js',
                                'components/knitters/knittersEdit.controller.js',
                                'components/knitters/knitters.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.knitters.index', {
                url: '/knitters',
                templateUrl: 'components/knitters/index.html',
                controller: 'KnittersListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.knitters.new', {
                url: '/knitters/new',
                templateUrl: 'components/knitters/new.html',
                controller: 'KnittersNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.knitters.edit', {
                url: '/knitters/edit/{knitterId:string}',
                templateUrl: 'components/knitters/new.html',
                controller: 'KnittersEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();