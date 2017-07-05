;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.designs', {
                template: '<ui-view />',
                controller: 'DesignsParentCtrl',
                controllerAs: 'parentCtrl',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/designs/designsParent.controller.js',
                                'components/designs/designsList.controller.js',
                                'components/designs/designsNew.controller.js',
                                'components/designs/designsEdit.controller.js',
                                'components/designs/designs.factory.js',
                                'components/designs/property/properties.factory.js',
                                'components/customers/customers.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.designs.index', {
                url: '/designs',
                templateUrl: 'components/designs/index.html',
                controller: 'DesignsListCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.designs.new', {
                url: '/designs/new',
                templateUrl: 'components/designs/new.html',
                controller: 'DesignsNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.designs.edit', {
                url: '/designs/edit/{designId:string}',
                templateUrl: 'components/designs/new.html',
                controller: 'DesignsEditCtrl',
                controllerAs: 'ctrl',
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();