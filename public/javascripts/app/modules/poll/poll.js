angular.module('etcs.poll', [])
    .controller('PollController', function ($scope, $http, $location, constants) {
        $scope.snippet1 = "";
        $scope.snippet2 = "";

        $scope.snippet1VoteButtonHovered = false;
        $scope.snippet2VoteButtonHovered = false;

        $http.get(constants.api.poll($scope.token)).success(function (data) {
            $scope.snippet1 = data.snippet1;
            $scope.snippet2 = data.snippet2;
        });

        $scope.voteSnippet1 = function () {
            $http.post(constants.api.voteSnippet1($scope.token), {})
                .success(function (data) {
                    $location.path(constants.urls.result(data.token));
                });
        };

        $scope.voteSnippet2 = function () {
            $http.post(constants.api.voteSnippet2($scope.token), {})
                .success(function (data) {
                    $location.path(constants.urls.result(data.token));
                });
        };
    });
