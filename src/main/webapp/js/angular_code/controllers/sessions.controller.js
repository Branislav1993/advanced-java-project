module.exports = function (app) {

    app.controller("SessionsCtrl", SessionsCtrl);

    SessionsCtrl.$inject = ['Sessions'];
    function SessionsCtrl(Sessions) {

        var ctrl = this;
        ctrl.create = create;
        ctrl.remove = remove;
        //ctrl.update = update;

        ctrl.sessions = [];
        list();
        ctrl.newSession = {};

        //GET ALL
        function list() {
            Sessions.getList().then(function (sessions) {
                ctrl.sessions = sessions;
            });
        }

        //CREATE
        function create() {
            Sessions.post(ctrl.newSession).then(function (response) {
                ctrl.newSession = {};
                console.log(JSON.stringify(response));
            });
        }

        // DELETE
        function remove(sessionId) {
            Sessions.one(sessionId).remove().then(function () {
                ctrl.list();
            });
        }
    }
};
