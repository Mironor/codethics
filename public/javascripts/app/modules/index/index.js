angular.module('etcs.index', [])
    .controller('IndexController', function ($scope, $http, $location, constants) {
        $scope.snippet1 = "";
        $scope.snippet2 = "";

        $scope.submitSnippets = function () {
            if ($scope.snippet1 && $scope.snippet2){
                $http.post(constants.api.submit, {
                    snippet1: $scope.snippet1,
                    snippet2: $scope.snippet2
                }).success(function (data) {
                    $location.path(constants.urls.poll(data.token));
                })
            }
        }
    });
