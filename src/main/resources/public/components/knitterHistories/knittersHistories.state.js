;
(function () {
    'use strict';
    var state = function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            debug: false,
            events: true,
        });
        $stateProvider
            .state('dashboard.knittingHistory', {
                template: '<ui-view />',
                resolve: {
                    loadMyFiles: function ($ocLazyLoad) {
                        return $ocLazyLoad.load({
                            name: 'sbAdminApp',
                            files: [
                                'components/knitterHistories/knittersHistoriesList.controller.js',
                                'components/knitterHistories/knittersHistoriesNew.controller.js',
                                'components/knitterHistories/knittersHistories.factory.js',
                                'components/machines/machines.factory.js',
                                'components/knitters/knitters.factory.js',
                                'components/shared/resources.js'
                            ]
                        })
                    }
                }
            })
            .state('dashboard.knittingHistory.index', {
                url: '/knitterHistories',
                templateUrl: 'components/knitterHistories/index.html',
                controller: 'KnittersHistoryListCtrl',
                controllerAs: 'ctrl'
            })
            .state('dashboard.knittingHistory.new', {
                url: '/knitterHistories/new/{:clothId:string}',
                templateUrl: 'components/knitterHistories/new.html',
                controller: 'KnittersHistoryNewCtrl',
                controllerAs: 'ctrl'
            })
    }
    state.$inject = ['$stateProvider', '$urlRouterProvider', '$ocLazyLoadProvider'];
    angular.module('sbAdminApp')
        .config(state);
})
();