module.exports = function (app) {

    app.factory("Members", Members);
    app.factory("Speeches", Speeches);
    app.factory("Towns", Towns);
    app.factory("Sessions", Sessions);
    app.factory("Parties", Parties);

    Members.$inject = ['Restangular'];
    function Members(Restangular) {
        return Restangular.service('members');
    }

    Speeches.$inject = ['Restangular'];
    function Speeches(Restangular) {
        return Restangular.service('speeches');
    }

    Towns.$inject = ['Restangular'];
    function Towns(Restangular) {
        return Restangular.service('towns');
    }

    Sessions.$inject = ['Restangular'];
    function Sessions(Restangular) {
        return Restangular.service('sessions');
    }

    Parties.$inject = ['Restangular'];
    function Parties(Restangular) {
        return Restangular.service('parties');
    }
};