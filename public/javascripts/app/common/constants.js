// Inject server variable
var pathToApp = window.pathToApp || 'public/javascripts/app/'; // path for karma tests if undefined

angular.module('etcs')
    .constant('constants', {
        pathToApp: pathToApp,

        api: {
            submit: '/submit',
            poll: function (token) {
                return '/poll/' + token
            },
            result: function (token) {
                return '/result/' + token
            },
            voteSnippet1: function (token) {
                return '/vote-one/' + token
            },
            voteSnippet2: function (token) {
                return '/vote-two/' + token
            }
        },

        urls: {
            poll: function (token) {
                return '/p/' + token
            },
            result: function (token) {
                return '/r/' + token
            }
        }
    });
