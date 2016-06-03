var angular = require('./node_modules/angular/index.js');
var restangular = require('./node_modules/restangular/src/restangular.js');
require('./node_modules/restangular/node_modules/lodash/index.js');
require('./node_modules/angular-animate/index.js');
require('./node_modules/angular-sanitize/index.js');
require('./node_modules/angular-scroll/index.js');
require('./node_modules/angular-route/index.js');
require('./node_modules/angular-loading-bar/index.js');
require('./node_modules/angular-local-storage/index.js');

var app = angular
    .module('app', ['restangular', 'ngRoute', 'ngAnimate', 'angular-loading-bar', 'LocalStorageModule'])
    .constant('appConfig', {
        baseUrl: "http://localhost:8080/parlament/api/"
    })
    .config(['$httpProvider', 'appConfig', function ($httpProvider, appConfig) {

        $httpProvider.interceptors.push(function ($q) {
            return {
                'responseError': function (response) {

                    var statusCode = response.status;

                    // Ignore client request cancelled
                    if (statusCode === 401) {
                        window.location = appConfig.baseUrl + '/auth/login';
                        return response;
                    }
                    // Reject via $q, otherwise the error will pass as success
                    return $q.reject(response);
                }
            };
        });
    }])
    .config(['RestangularProvider', 'appConfig', function (RestangularProvider, appConfig) {
        // Set base url for all API requests
        RestangularProvider.setBaseUrl(appConfig.baseUrl);

        // Extract object list from paginated response
        RestangularProvider.addResponseInterceptor(function (data, operation, what, url, response, deferred) {
            var extractedData;
            if ('getList' === operation && typeof data.data !== 'undefined') {
                extractedData = data.data;
                extractedData.pagination = {};
                for (var prop in data) {
                    if (data.hasOwnProperty(prop) && 'data' !== prop) {
                        extractedData.pagination[prop] = data[prop];
                    }
                }
            } else {
                extractedData = data;
            }
            return extractedData;
        });
    }]);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
        .when('/members', {
            templateUrl: 'views/members.html',
            controller: 'MembersCtrl',
            controllerAs: 'mctrl'
        })
        .when('/create-member', {
            templateUrl: 'views/create-member.html',
            controller: 'CreateMemberCtrl',
            controllerAs: 'mctrl'
        })
        .when('/parties', {
            templateUrl: 'views/parties.html',
            controller: 'PartiesCtrl',
            controllerAs: 'pctrl'
        })
        .when('/sessions', {
            templateUrl: 'views/sessions.html',
            controller: 'SessionsCtrl',
            controllerAs: 'sctrl'
        })
        .when('/speeches', {
            templateUrl: 'views/speeches.html',
            controller: 'SpeechesCtrl',
            controllerAs: 'spctrl'
        })
}]);


//controllers
require(__dirname + '/angular_code/controllers/Demo.controller.js')(app);
require(__dirname + '/angular_code/controllers/parties.controller.js')(app);
require(__dirname + '/angular_code/controllers/createMember.controller.js')(app);
require(__dirname + '/angular_code/controllers/members.controller.js')(app);
require(__dirname + '/angular_code/controllers/speeches.controller.js')(app);
require(__dirname + '/angular_code/controllers/sessions.controller.js')(app);

//factories
require(__dirname + '/angular_code/factories/factory.js')(app);

//directives
require(__dirname + '/angular_code/directives/paginator.directive.js')(app);