module.exports = function (app) {

    app.controller("CreateSpeechCtrl", CreateSpeechCtrl);

    CreateSpeechCtrl.$inject = ['Members', 'Speeches', 'Sessions', 'localStorageService', 'Restangular', 'dialogs'];
    function CreateSpeechCtrl(Members, Speeches, Sessions, localStorageService, Restangular, dialogs) {

        var ctrl = this;
        ctrl.create = create;
        ctrl.update = update;
        ctrl.searchMembers = searchMembers;
        ctrl.changeMembersPage = changeMembersPage;
        ctrl.changeSessionsPage = changeSessionsPage;

        ctrl.editedSpeech = {};
        ctrl.editMode = false;

        ctrl.members = [];
        ctrl.sessions = [];

        ctrl.searchMemberTerm = null;
        ctrl.currentMembersPage = null;
        ctrl.currentSessionsPage = null;

        //SEARCH MEMBERS
        function searchMembers() {
            ctrl.currentMembersPage = 1;
            listMembers();
        }

        //GET ALL MEMBERS
        function listMembers() {
            Members.getList({page: ctrl.currentMembersPage, query: ctrl.searchMemberTerm}).then(function (members) {
                ctrl.members = members;
            });
        }

        //CHANGE MEMBERS PAGE
        function changeMembersPage(page) {
            ctrl.currentMembersPage = page;
            listMembers();
        }

        //CHANGE SESSIONS PAGE
        function changeSessionsPage(page) {
            ctrl.currentSessionsPage = page;
            listSessions();
        }

        //GET ALL SESSIONS
        function listSessions() {
            Sessions.getList({page: ctrl.currentSessionsPage}).then(function (sessions) {
                ctrl.sessions = sessions;
            });
        }

        // CHECK PAGE MODE - EDIT || CREATE
        if (localStorageService.get("editedSpeech") != null) {
            ctrl.editedSpeech = localStorageService.get("editedSpeech");
            ctrl.editMode = true;
        }

        //CREATE
        function create() {
            Speeches.post(ctrl.editedSpeech).then(function (response) {
                ctrl.editedSpeech = {};
                dialogs.notify("Success!", "Speech successfully created!", {size: "md"});
            });
        }

        //UPDATE
        function update() {
            ctrl.putSpeech = Restangular.copy(ctrl.editedSpeech);
            ctrl.putSpeech.put().then(function (speech) {
                ctrl.editedSpeech = speech;
                dialogs.notify("Success!", "Speech successfully updated!", {size: "md"});
            })
        }

    }
};
