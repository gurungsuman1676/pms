/**
 * Created by arrowhead on 10/19/15.
 */

'use strict';
angular
    .module('sbAdminApp')
    .value('selectizeConfig',{
        create: false,
        valueField: 'id',
        labelField: 'name',
        searchField: ['name'],
        maxItems: 1,
        maxOptions: 10,
    })
    .directive('coreSelectize', [
        '$timeout', function ($timeout) {
            var display;
            display = true;
            return {
                require: 'ngModel',
                scope: {
                    coreSelectize: '='
                },
                link: function (scope, element, attrs, ngModelCtrl) {

                    scope.$watch('coreSelectize', function (value) {

                        if (value) {
                            var init, selectize, set_initial_options, watch;
                            selectize = void 0;
                            set_initial_options = function () {
                                var initialOptions;
                                initialOptions = scope.coreSelectize.wInitialOptions;
                                if (initialOptions) {
                                    return _.each(initialOptions, function (option) {
                                        return selectize.addOption(option);
                                    });
                                }
                            };
                            watch = function () {
                                var watcher;
                                watcher = function () {
                                    return ngModelCtrl.$modelValue;
                                };
                                return scope.$watch(watcher, function (newVal) {
                                    if (!angular.equals(newVal, selectize.getValue())) {
                                        return $timeout(function () {
                                            return selectize.setValue(newVal);
                                        });
                                    }
                                });
                            };
                            init = function () {
                                var $select;
                                $select = element.selectize(scope.coreSelectize);
                                selectize = $select[0].selectize;
                                set_initial_options();
                                return watch();
                            };
                            return init();
                        }
                    })
                }
            };
        }
    ]);
