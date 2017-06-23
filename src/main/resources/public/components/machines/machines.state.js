;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.machines', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/machines/machinesList.controller.js',
                                'components/machines/machinesNew.controller.js',
                                'components/machines/machinesEdit.controller.js',
                                'components/machines/machines.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.machines.index', {
                url: '/machines',
                templateUrl: 'components/machines/index.html',
                controller: 'MachinesListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.machines.new', {
                url: '/machines/new',
                templateUrl: 'components/machines/new.html',
                controller: 'MachinesNewCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.machines.edit', {
                url: '/machines/edit/{machineId:string}',
                templateUrl: 'components/machines/new.html',
                controller: 'MachinesEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();