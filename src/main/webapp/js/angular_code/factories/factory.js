module.exports = function (app) {

    app.factory("Members", Members);
    app.factory("Speeches", Speeches);
    app.factory("Towns", Towns);
    app.factory("Sessions", Sessions);
    app.factory("Parties", Parties);
    app.factory("MemberSpeeches", MemberSpeeches);
    app.factory("SessionSpeeches", SessionSpeeches);

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

    MemberSpeeches.$inject = ['Restangular'];
    function MemberSpeeches(Restangular) {
        return {
            forMember: function (memberId) {
                return Restangular.service('speeches', Restangular.one('members', memberId));
            }
        };
    }

    SessionSpeeches.$inject = ['Restangular'];
    function SessionSpeeches(Restangular) {
        return {
            forSession: function (sessionId) {
                return Restangular.service('speeches', Restangular.one('sessions', sessionId));
            }
        };
    }
};