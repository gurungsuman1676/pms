angular.module('sbAdminApp')
    .directive("ngFileSelect", function () {

        return {
            link: function ($scope, el) {

                el.bind("change", function (e) {
                    $scope.getFile((e.srcElement || e.target).files[0]);
                })

            }
        }


    });