angular.module('etcs.result', [])
    .controller('ResultController', function ($scope, $http, $location, constants) {
        $scope.snippet1 = "";
        $scope.snippet2 = "";

        $scope.snippet1VoteButtonHovered = false;
        $scope.snippet2VoteButtonHovered = false;

        $http.get(constants.api.result($scope.token)).success(function (data) {
            $scope.snippet1 = data.snippet1;
            $scope.snippet1Votes = data.snippet1Votes;
            $scope.snippet2 = data.snippet2;
            $scope.snippet2Votes = data.snippet2Votes;
        });
    });
