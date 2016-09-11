;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.users', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/users/usersList.controller.js',
                                'components/users/usersNew.controller.js',
                                'components/users/usersEdit.controller.js',
                                'components/users/users.factory.js',
                                'components/locations/locations.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.users.index', {
                url: '/users',
                templateUrl: 'components/users/index.html',
                controller: 'UsersListCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.users.new', {
                url: '/users/new',
                templateUrl: 'components/users/new.html',
                controller: 'UsersNewCtrl',
                controllerAs: 'ctrl',
            })
            .state('dashboard.users.edit', {
                url: '/users/edit/{userId:string}',
                templateUrl: 'components/users/new.html',
                controller: 'UsersEditCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();