;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.locations', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/locations/LocationsList.controller.js',
                                'components/locations/locationsNew.controller.js',
                                'components/locations/locationsEdit.controller.js',
                                'components/locations/locations.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.locations.index', {
                url: '/locations',
                templateUrl: 'components/locations/index.html',
                controller: 'LocationsListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.locations.new', {
                url: '/locations/new',
                templateUrl: 'components/locations/new.html',
                controller: 'LocationsNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.locations.edit', {
                url: '/locations/edit/{locationId:string}',
                templateUrl: 'components/locations/new.html',
                controller: 'LocationsEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();