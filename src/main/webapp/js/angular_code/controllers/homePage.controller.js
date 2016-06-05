module.exports = function (app) {

    app.controller("HomePageCtrl", HomePageCtrl);

    HomePageCtrl.$inject = ['$document', '$location', '$anchorScroll'];
    function HomePageCtrl($document, $location, $anchorScroll) {

        var ctrl = this;

        ctrl.scrollTo = scrollTo;

        function scrollTo(id) {
            var someElement = angular.element(document.getElementById(id));
            $document.scrollToElement(someElement, 30, 750);
        }

    }
};
