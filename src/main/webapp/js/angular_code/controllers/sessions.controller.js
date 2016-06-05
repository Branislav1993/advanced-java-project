module.exports = function (app) {

    app.controller("SessionsCtrl", SessionsCtrl);

    SessionsCtrl.$inject = ['Sessions', 'localStorageService', 'dialogs'];
    function SessionsCtrl(Sessions, localStorageService, dialogs) {

        var ctrl = this;
        ctrl.list = list;
        ctrl.remove = remove;
        ctrl.changePage = changePage;
        ctrl.search = search;
        ctrl.updateSession = updateSession;
        ctrl.createSession = createSession;
        ctrl.showTranscript = showTranscript;
        ctrl.showAgenda = showAgenda;
        ctrl.seeSpeeches = seeSpeeches;

        ctrl.sessions = [];
        ctrl.currentPage = null;
        ctrl.searchTerm = null;

        //GET ALL
        function list() {
            Sessions.getList({page: ctrl.currentPage, query: ctrl.searchTerm}).then(function (sessions) {
                ctrl.sessions = sessions;
            });
        }

        //SEARCH
        function search() {
            ctrl.currentPage = 1;
            list();
        }

        // DELETE
        function remove(sessionId) {
            var dlg = dialogs.confirm("Are you sure?", "Do you want to delete selected session?", {size: "md"});
            dlg.result.then(function () {
                Sessions.one(sessionId).remove().then(function () {
                    list();
                }, function (response) {
                    dialogs.notify("Error!", response.error, null);
                });
            });
        }

        //CHANGE PAGE
        function changePage(page) {
            ctrl.currentPage = page;
            list();
        }

        function createSession() {
            localStorageService.remove("editedSession");
            window.location = '#/create-session';
        }

        function updateSession(member) {
            localStorageService.set("editedSession", member);
            window.location = "#/create-session";
        }

        function showTranscript(s) {
            dialogs.notify('Transcript: ' + s.date, s.transcriptText, null);
        }

        function showAgenda(s) {
            dialogs.notify('Agenda: ' + s.date, s.agenda, null);
        }

        function seeSpeeches(s) {
            localStorageService.set("session", s);
            window.location = '#/session-speeches';
        }

    }
};

