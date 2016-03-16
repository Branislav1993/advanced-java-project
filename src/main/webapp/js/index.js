var angular = require('./node_modules/angular/index.js');
var restangular = require('./node_modules/restangular/src/restangular.js');
require('./node_modules/restangular/node_modules/lodash/index.js');

var app = angular
    .module('app', ['restangular'])
    .constant('appConfig', {
        baseUrl:  "http://localhost:8080/parlament"
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


//controllers
require(__dirname + '/angular_code/controllers/Demo.controller.js')(app);

//factories
require(__dirname + '/angular_code/factories/factory.js')(app);