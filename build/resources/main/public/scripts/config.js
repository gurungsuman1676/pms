angular.module('sbAdminApp')
    .config(function ($urlRouterProvider) {
        $urlRouterProvider.otherwise('/login');
    });