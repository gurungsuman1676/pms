;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider

            .state('dashboard', {
                templateUrl: 'components/dashboard/main.html',
                controller: 'DashboardCtrl',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'scripts/directives/header/header.js',
                                'scripts/directives/header/header-notification/header-notification.js',
                                'scripts/directives/sidebar/sidebar.js',
                                'scripts/directives/sidebar/sidebar-search/sidebar-search.js',
                                'scripts/directives/footer/footer.js',
                                'components/dashboard/dashboard.controller.js',
                            ]
                        })
                    }
                }
            })
            //.state('dashboard.home', {
            //    url: '/home',
            //    controller: 'MainCtrl',
            //    templateUrl: 'components/dashboard/home.html',
            //})
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();