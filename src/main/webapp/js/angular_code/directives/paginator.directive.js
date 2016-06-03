module.exports = function (app) {

    app.directive('paginator', paginator);

    function paginator() {
        return {
            restrict: 'E',
            scope: {
                collection: '=',
                changePage: '&'
            },
            templateUrl: './././views/paginator.html',
            link: function (scope, elem, attrs) {
                scope.changePage()(1);
            }
        };
    }
};