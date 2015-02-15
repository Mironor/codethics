angular.module('etcs', [
    'ui.router',
    'ngMaterial',
    'ngMessages',
    'hljs',
    'etcs.index',
    'etcs.poll',
    'etcs.result'
]).config(function ($stateProvider, $urlRouterProvider, $locationProvider, constants) {
    $stateProvider
        .state('index', {
            url: '/',
            templateUrl: constants.pathToApp + 'modules/index/index.html'
        })
        .state('poll', {
            url: '/p/:token',
            templateUrl: constants.pathToApp + 'modules/poll/poll.html',
            controller: function($scope, $stateParams){
                $scope.token = $stateParams.token;
            }
        })
        .state('result', {
            url: '/r/:token',
            templateUrl: constants.pathToApp + 'modules/result/result.html',
            controller: function($scope, $stateParams){
                $scope.token = $stateParams.token;
            }
        });

    $urlRouterProvider.otherwise('/');

    $locationProvider.html5Mode(true);
}).config(function ($mdThemingProvider) {
    // Configure a dark theme with primary foreground yellow
    $mdThemingProvider.theme('default')
        .primaryPalette('blue')
});
