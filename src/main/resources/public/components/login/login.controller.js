'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('LoginCtrl', function ($rootScope, $scope, LoginFactory, $localStorage, Flash) {

        var self = this;
        self.login = function () {
            LoginFactory.login(self.user, function (response) {
                    $localStorage.user = {
                        username: response.username,
                        token: response.token,
                        roles: response.roles
                    };
                    $rootScope.loginSuccess();
                }, function (response) {
                    Flash.create('danger', response.message, 'custom-class');
                }
            )
            ;
        }

    })
;