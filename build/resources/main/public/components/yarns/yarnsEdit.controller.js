'use strict';
/**
 * @ngdoc function
 * @name sbAdminApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the sbAdminApp
 */
angular.module('sbAdminApp')
    .controller('YarnsEditCtrl', function ($scope, $stateParams, YarnsFactory, $state, Flash) {

        var self = this;
        self.disableEdit = true;

        YarnsFactory.getYarn($stateParams.yarnId, function (response) {
            self.yarn = response;
        }, function (response) {
            // TODO error handling;
            Flash.create('danger', response.message, 'custom-class');
        });

        self.submitYarn = function () {
            YarnsFactory.updateYarn(self.yarn, function (response) {
                $state.go('dashboard.yarns.index');
                Flash.create('success', 'Yarn updated successfully', 'custom-class');
            }, function (response) {
                Flash.create('danger', response.message, 'custom-class');
            })
        }
    });
