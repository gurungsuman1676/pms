'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description
 * # sbAdminApp
 *
 * Main module of the application.
 */
angular
    .module('sbAdminApp', [
        'selectize',
        'oc.lazyLoad',
        'ui.router',
        'ui.bootstrap',
        'flash',
        'angular-loading-bar',
        'ngDialog',
        'ngTable',
        'ngTableExport',
        'AngularPrint',
        'kendo.directives',
        'ngStorage',
        'ngFileUpload'
    ])
    .config(['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider', '$httpProvider',
        function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, $httpProvider) {

            $httpProvider.interceptors.push(['$rootScope', '$localStorage',
                function ($rootScope, $localStorage) {

                    return {
                        'request': function (config) {
                            if ($rootScope.isLoggedIn()) {
                                config.headers['x-auth-token'] = $localStorage.user.token;
                            }
                            return config;
                        }
                    }
                }]);

            $ocLazyLoadProvider.config({
                debug: false,
                events: true,
            });

            $stateProvider
                .state('login', {
                    templateUrl: 'components/login/login.html',
                    url: '/login',
                    controller: 'LoginCtrl',
                    controllerAs: 'ctrl'
                });
        }])
    .filter('trustUrl', function ($sce) {
        return function (url) {
            return $sce.trustAsResourceUrl(url);
        };
    })
    .run(['$rootScope', '$state', '$localStorage', function ($rootScope, $state, $localStorage) {

        $rootScope.isLoggedIn = function () {
            var user = $localStorage.user
            return angular.isDefined(user) &&
                angular.isDefined(user.username) &&
                angular.isDefined(user.token) &&
                angular.isDefined(user.roles)
        }

        $rootScope.isAuthenticated = function () {
            if ($rootScope.isLoggedIn()) {
                return true;
            }
            return false;
        }

        $rootScope.loginSuccess = function () {
            $localStorage.isShawlUser = $localStorage.user.type === 'SHAWL';

            if (($localStorage.user.roles).indexOf("ADMIN") != -1) {
                $localStorage.isAdmin = true;
                $localStorage.isShipper = false;
                $localStorage.isKnitter = false;
                $state.go('dashboard.clothes.index');
            }
            else {

                if (($localStorage.user.roles).indexOf("SHIPPING") != -1) {
                    $localStorage.isShipper = true;

                } else {
                    $localStorage.isShipper = false;

                }
                if (($localStorage.user.roles).indexOf("PRE-KNITTING") != -1) {
                    $localStorage.isKnitter = true;

                } else {
                    $localStorage.isKnitter = false;

                }
                $localStorage.isAdmin = false;

                if ($localStorage.isShawlUser) {
                    $state.go('dashboard.inventory.index');
                } else {
                    $state.go('dashboard.supervisors.index');

                }
            }


        }

        $rootScope.$on('$stateChangeStart', function (event, next) {
            if (next.name != 'login') {
                if (!$rootScope.isAuthenticated()) {
                    event.preventDefault();
                }
            }
            else {
                if ($rootScope.isLoggedIn()) {
                    event.preventDefault();
                    $rootScope.loginSuccess();
                }
            }
        })
    }]);


